package com.marcoswolf.crm.reparos.ui.controller.cliente;

import static com.marcoswolf.crm.reparos.ui.utils.TextFieldUtils.*;

import com.marcoswolf.crm.reparos.business.ClienteService;
import com.marcoswolf.crm.reparos.business.EstadoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Endereco;
import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import com.marcoswolf.crm.reparos.ui.utils.FormValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ClienteFormController {
    private final EstadoService estadoService;
    private final ClienteService clienteService;

    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtCidade;
    @FXML private ComboBox<Estado> comboEstado;
    @FXML private TextField txtBairro;
    @FXML private TextField txtCep;
    @FXML private TextField txtLogradouro;
    @FXML private TextField txtNumero;

    @FXML private AnchorPane rootPane;

    @FXML
    private void initialize() {
        aplicarFiltros();
    }

    private void carregarEstados() {
        List<Estado> estados = estadoService.listarTodos();

        Estado selecione = new Estado();
        selecione.setId(0L);
        selecione.setNome("Selecione");

        estados.add(0, selecione);

        comboEstado.getItems().setAll(estados);
        comboEstado.getSelectionModel().selectFirst();

        comboEstado.setConverter(new StringConverter<>() {
            @Override
            public String toString(Estado estado) {
                return estado == null ? "" : estado.getNome();
            }

            @Override
            public Estado fromString(String string) {
                return comboEstado.getItems().stream()
                        .filter(e -> e.getNome().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    private void aplicarFiltros() {
        aplicarLimite(txtNome, 50);
        aplicarMascaraTelefone(txtTelefone);
        aplicarLimite(txtEmail, 80);
        aplicarLimite(txtCidade, 50);
        aplicarLimite(txtBairro, 50);
        aplicarMascaraCEP(txtCep);
        aplicarLimite(txtLogradouro, 80);
        aplicarLimite(txtNumero, 8);
        carregarEstados();
    }

    @FXML
    private void onCancelar() {
        ((AnchorPane) rootPane.getParent()).getChildren().remove(rootPane);
    }

    @FXML
    private void onSalvar() {
        Map<String, Object> campos = new LinkedHashMap<>();
        campos.put("Nome", txtNome);
        campos.put("Telefone", txtTelefone);
        campos.put("Cidade", txtCidade);
        campos.put("Estado", comboEstado);

        if (!FormValidator.validarCamposObrigatorios(campos)) return;

        Endereco endereco = Endereco.builder()
                .cidade(txtCidade.getText())
                .bairro(txtBairro.getText())
                .logradouro(txtLogradouro.getText())
                .numero(parseInteger(txtNumero))
                .cep(txtCep.getText())
                .estado(comboEstado.getValue())
                .build();

        Cliente cliente = Cliente.builder()
                .nome(txtNome.getText())
                .telefone(txtTelefone.getText())
                .email(txtEmail.getText())
                .endereco(endereco)
                .build();

        try {
            clienteService.salvarCliente(cliente);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Cliente salvo com sucesso!");
            alert.showAndWait();

            ((AnchorPane) rootPane.getParent()).getChildren().remove(rootPane);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao salvar");
            alert.setHeaderText("Não foi possível salvar o cliente");
            alert.setContentText(e.getMessage() != null ? e.getMessage() : "Erro desconhecido.");
            alert.showAndWait();
        }
    }
}
package com.marcoswolf.crm.reparos.ui.controller.cliente;

import static com.marcoswolf.crm.reparos.ui.utils.TextFieldUtils.*;

import com.marcoswolf.crm.reparos.business.cliente.ClienteService;
import com.marcoswolf.crm.reparos.business.EstadoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Endereco;
import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
import com.marcoswolf.crm.reparos.ui.utils.FormValidator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class ClienteFormController {
    private final EstadoService estadoService;
    private final ClienteService clienteService;

    private final MainViewController mainViewController;

    private Cliente clienteEditando;

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
    @FXML private Label lblTitulo;
    @FXML private Button btnExcluir;

    @FXML
    private void initialize() {
        aplicarFiltros();
        limparFormulario();
        clienteEditando = null;
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
    private void onVoltar() {
        limparFormulario();
        mainViewController.abrirTela("/fxml/cliente/cliente-gerenciar.fxml", null);
    }

    @FXML
    private void onExcluir() {
        if (clienteEditando == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar exclusão");
        confirm.setHeaderText("Deseja realmente excluir este cliente?");
        confirm.setContentText("Esta ação não poderá ser desfeita.");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    clienteService.deletarCliente(clienteEditando.getId());

                    Alert ok = new Alert(Alert.AlertType.INFORMATION);
                    ok.setTitle("Sucesso");
                    ok.setHeaderText(null);
                    ok.setContentText("Cliente removido com sucesso!");
                    ok.showAndWait();
                    limparFormulario();
                    mainViewController.abrirTela("/fxml/cliente/cliente-gerenciar.fxml", null);
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro ao excluir");
                    alert.setHeaderText("Não foi possível excluir o cliente");
                    alert.setContentText(e.getMessage() != null ? e.getMessage() : "Erro desconhecido.");
                    alert.showAndWait();
                }
            }
        });
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

        try {
            if (clienteEditando == null) {
                // Novo Cliente
                Cliente cliente = Cliente.builder()
                        .nome(txtNome.getText())
                        .telefone(txtTelefone.getText())
                        .email(txtEmail.getText())
                        .endereco(endereco)
                        .build();

                clienteService.salvarCliente(cliente);
            } else {
                clienteEditando.setNome(txtNome.getText());
                clienteEditando.setTelefone(txtTelefone.getText());
                clienteEditando.setEmail(txtEmail.getText());
                clienteEditando.setEndereco(endereco);

                clienteService.salvarCliente(clienteEditando);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Cliente salvo com sucesso!");
            alert.showAndWait();
            limparFormulario();
            mainViewController.abrirTela("/fxml/cliente/cliente-gerenciar.fxml", null);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao salvar");
            alert.setHeaderText("Não foi possível salvar o cliente");
            alert.setContentText(e.getMessage() != null ? e.getMessage() : "Erro desconhecido.");
            alert.showAndWait();
        }
    }

    // Edição
    public void setData(Cliente cliente) {
        this.clienteEditando = cliente;

        if (cliente != null) {
            lblTitulo.setText("Editar Cliente");
            btnExcluir.setVisible(true);

            txtNome.setText(cliente.getNome());
            txtTelefone.setText(cliente.getTelefone());
            txtEmail.setText(cliente.getEmail());

            if (cliente.getEndereco() != null) {
                txtCidade.setText(cliente.getEndereco().getCidade());
                txtBairro.setText(cliente.getEndereco().getBairro());
                txtCep.setText(cliente.getEndereco().getCep());
                txtLogradouro.setText(cliente.getEndereco().getLogradouro());
                txtNumero.setText(cliente.getEndereco().getNumero() != null
                        ? cliente.getEndereco().getNumero().toString() : "");
                comboEstado.setValue(cliente.getEndereco().getEstado());
            }
        } else {
            lblTitulo.setText("Cadastrar Cliente");
            btnExcluir.setVisible(false);
        }
    }

    public Cliente getData() {
        return clienteEditando;
    }

    private void limparFormulario() {
        txtNome.clear();
        txtTelefone.clear();
        txtEmail.clear();
        txtCidade.clear();
        txtBairro.clear();
        txtCep.clear();
        txtLogradouro.clear();
        txtNumero.clear();
        comboEstado.getSelectionModel().selectFirst();
        lblTitulo.setText("Cadastrar Cliente");
        btnExcluir.setVisible(false);
        clienteEditando = null;
    }
}
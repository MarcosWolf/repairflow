package com.marcoswolf.crm.reparos.ui.controller.cliente;

import com.marcoswolf.crm.reparos.business.estado.EstadoService;
import com.marcoswolf.crm.reparos.business.cliente.IClienteComandoService;
import com.marcoswolf.crm.reparos.business.cliente.IClienteConsultaService;
import com.marcoswolf.crm.reparos.business.estado.IEstadoConsultaService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
import com.marcoswolf.crm.reparos.ui.interfaces.DataReceiver;
import com.marcoswolf.crm.reparos.ui.mappers.ClienteFormMapper;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import com.marcoswolf.crm.reparos.ui.utils.FormValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.marcoswolf.crm.reparos.ui.utils.TextFieldUtils.*;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class ClienteFormController implements DataReceiver<Cliente> {
    private final EstadoService estadoService;
    private final ClienteFormMapper formMapper;
    private final AlertService alertService;
    @Autowired
    private final ViewNavigator navigator;

    @FXML private AnchorPane rootPane;

    private final IClienteComandoService clienteComandoService;
    private final IEstadoConsultaService estadoConsultaService;

    private Cliente clienteEditando;

    @FXML private TextField txtNome, txtTelefone, txtEmail, txtCidade, txtBairro, txtCep, txtLogradouro, txtNumero;
    @FXML private ComboBox<Estado> comboEstado;
    @FXML private Label lblTitulo;
    @FXML private Button btnExcluir;

    @FXML
    private void initialize() {
        carregarEstados();
        aplicarFiltros();
    }

    @Override
    public void setData(Cliente cliente) {
        this.clienteEditando = cliente;
        preencherFormulario(cliente);
    }

    @FXML
    private void onSalvar() {
        Map<String, Object> campos = new LinkedHashMap<>();
        campos.put("Nome", txtNome);
        campos.put("Telefone", txtTelefone);
        campos.put("Cidade", txtCidade);
        campos.put("Estado", comboEstado);

        if (!FormValidator.validarCamposObrigatorios(campos)) return;

        try {
            Cliente cliente = formMapper.toEntity(
                    txtNome, txtTelefone, txtEmail,
                    txtCidade, txtBairro, txtCep,
                    txtLogradouro, txtNumero, comboEstado
            );

            if (clienteEditando == null) {
                clienteComandoService.salvarCliente(cliente);
            } else {
                cliente.setId(clienteEditando.getId());
                clienteComandoService.salvarCliente(cliente);
            }

            alertService.info("Sucesso", "Cliente salvo com sucesso!");
            limparFormulario();
            onVoltar();
        } catch (Exception e) {
            alertService.error("Erro ao salvar", e.getMessage());
        }
    }

    @FXML
    private void onExcluir() {
        if (clienteEditando == null) return;

        boolean confirmar = alertService.confirm("Confirmar exclusão",
                "Deseja realmente excluir este cliente?");

        if (!confirmar) return;

        try {
            clienteComandoService.deletarCliente(clienteEditando.getId());
            alertService.info("Sucesso", "Cliente removido com sucesso!");
            limparFormulario();
            onVoltar();
        } catch (Exception e) {
            alertService.error("Erro ao excluir", e.getMessage());
        }
    }

    @FXML
    private void onVoltar() {
        navigator.openView("/fxml/cliente/cliente-gerenciar.fxml", rootPane, null);
    }

    private void preencherFormulario(Cliente cliente) {
        if (cliente == null) {
            lblTitulo.setText("Cadastrar Cliente");
            btnExcluir.setVisible(false);
            limparCampos();
            return;
        }

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
        } else {
            limparCamposEndereco();
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtTelefone.clear();
        txtEmail.clear();
        limparCamposEndereco();
        comboEstado.getSelectionModel().selectFirst();
    }

    private void limparCamposEndereco() {
        txtCidade.clear();
        txtBairro.clear();
        txtCep.clear();
        txtLogradouro.clear();
        txtNumero.clear();
    }

    private void carregarEstados() {
        List<Estado> estados = estadoConsultaService.listarTodos();

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
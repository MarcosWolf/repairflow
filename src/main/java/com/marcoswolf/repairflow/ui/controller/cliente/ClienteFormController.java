package com.marcoswolf.repairflow.ui.controller.cliente;

import com.marcoswolf.repairflow.business.estado.EstadoConsultaService;
import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.infrastructure.entities.Estado;
import com.marcoswolf.repairflow.ui.handler.cliente.action.ClienteExcluirAction;
import com.marcoswolf.repairflow.ui.handler.cliente.dto.ClienteFormData;
import com.marcoswolf.repairflow.ui.handler.cliente.action.ClienteSalvarAction;
import com.marcoswolf.repairflow.ui.interfaces.DataReceiver;
import com.marcoswolf.repairflow.ui.navigation.ViewNavigator;
import com.marcoswolf.repairflow.ui.utils.*;
import com.marcoswolf.repairflow.ui.utils.ComboBoxUtils;
import com.marcoswolf.repairflow.ui.utils.MaskUtils;
import com.marcoswolf.repairflow.ui.utils.TextFieldUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.marcoswolf.repairflow.ui.utils.ParseUtils.parseInteger;

@Component
@RequiredArgsConstructor
public class ClienteFormController implements DataReceiver<Cliente> {
    private final ViewNavigator navigator;

    private final EstadoConsultaService estadoConsultaService;

    private final ClienteSalvarAction salvarAction;
    private final ClienteExcluirAction excluirAction;

    private Cliente novoCliente;

    @FXML private AnchorPane rootPane;
    @FXML private Label lblTitulo;
    @FXML private TextField txtNome, txtTelefone, txtEmail, txtDocumento, txtCidade, txtBairro, txtCep, txtLogradouro, txtNumero;
    @FXML private ComboBox<Estado> comboEstado;
    @FXML private Button btnExcluir;

    private static final String GERENCIAR_PATH = "/fxml/cliente/cliente-gerenciar.fxml";

    @FXML
    public void initialize() {
        configurarCampos();
        carregarEstados();
    }

    private void configurarCampos() {
        TextFieldUtils.aplicarLimite(txtNome, 50);
        TextFieldUtils.aplicarLimite(txtEmail, 80);
        TextFieldUtils.aplicarLimite(txtDocumento, 14);
        TextFieldUtils.aplicarLimite(txtCidade, 50);
        TextFieldUtils.aplicarLimite(txtBairro, 50);
        TextFieldUtils.aplicarLimite(txtLogradouro, 80);
        TextFieldUtils.aplicarLimite(txtNumero, 8);

        MaskUtils.aplicarMascaraCEP(txtCep);
        MaskUtils.aplicarMascaraTelefone(txtTelefone);
        MaskUtils.aplicarMascaraDocumento(txtDocumento);
    }

    private void carregarEstados() {
        ComboBoxUtils.carregarCombo(
                comboEstado,
                estadoConsultaService.listarTodos(),
                Estado::getNome,
                () -> {
                    Estado e = new Estado();
                    e.setId(0L);
                    e.setNome("Selecione");
                    return e;
                }
        );
    }

    @Override
    public void setData(Cliente cliente) {
        this.novoCliente = cliente;
        preencherFormulario(cliente);
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
        txtDocumento.setText(cliente.getDocumento());

        if (cliente.getEndereco() != null) {
            setEndereco(cliente);

        } else {
            limparCamposEndereco();
        }
    }

    private void setEndereco(Cliente cliente) {
        txtCidade.setText(cliente.getEndereco().getCidade());
        txtBairro.setText(cliente.getEndereco().getBairro());
        txtCep.setText(cliente.getEndereco().getCep());
        txtLogradouro.setText(cliente.getEndereco().getLogradouro());
        txtNumero.setText(cliente.getEndereco().getNumero() != null
                ? cliente.getEndereco().getNumero().toString()
                : "");
        comboEstado.setValue(cliente.getEndereco().getEstado());
    }

    private void limparCampos() {
        txtNome.clear();
        txtTelefone.clear();
        txtEmail.clear();
        txtDocumento.clear();
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

    @FXML
    private void salvar() {
        boolean sucesso = salvarAction.execute(novoCliente, criarFormData());
        if (sucesso) voltar();
    }

    @FXML
    private void excluir() {
        boolean sucesso = excluirAction.execute(novoCliente, null);
        if (sucesso) voltar();
    }

    @FXML
    private void voltar() {
        navigator.openViewRootPane(GERENCIAR_PATH, rootPane, null);
    }

    private ClienteFormData criarFormData() {
        return new ClienteFormData(
                txtNome.getText(),
                txtTelefone.getText(),
                txtEmail.getText(),
                txtDocumento.getText(),
                txtCidade.getText(),
                txtBairro.getText(),
                txtCep.getText(),
                txtLogradouro.getText(),
                parseInteger(txtNumero),
                comboEstado.getValue()
        );
    }
}
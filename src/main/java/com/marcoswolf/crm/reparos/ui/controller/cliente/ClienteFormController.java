package com.marcoswolf.crm.reparos.ui.controller.cliente;

import com.marcoswolf.crm.reparos.business.estado.IEstadoConsultaService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import com.marcoswolf.crm.reparos.ui.handler.cliente.ClienteExcluirAction;
import com.marcoswolf.crm.reparos.ui.handler.cliente.ClienteFormData;
import com.marcoswolf.crm.reparos.ui.handler.cliente.ClienteSalvarAction;
import com.marcoswolf.crm.reparos.ui.interfaces.DataReceiver;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import com.marcoswolf.crm.reparos.ui.utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.marcoswolf.crm.reparos.ui.utils.ParseUtils.parseInteger;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class ClienteFormController implements DataReceiver<Cliente> {
    private final ViewNavigator navigator;
    private final ClienteSalvarAction salvarAction;
    private final ClienteExcluirAction excluirAction;
    private Cliente novoCliente;
    private final IEstadoConsultaService estadoConsultaService;

    @FXML private AnchorPane rootPane;
    @FXML private Label lblTitulo;
    @FXML private TextField txtNome, txtTelefone, txtEmail, txtCidade, txtBairro, txtCep, txtLogradouro, txtNumero;
    @FXML private Button btnExcluir;
    @FXML private ComboBox<Estado> comboEstado;

    @FXML
    public void initialize() {
        configurarCampos();
        ComboBoxUtils.carregarEstados(comboEstado, estadoConsultaService);
    }

    private void configurarCampos() {
        TextFieldUtils.aplicarLimite(txtNome, 50);
        MaskUtils.aplicarMascaraTelefone(txtTelefone);
        TextFieldUtils.aplicarLimite(txtEmail, 80);
        TextFieldUtils.aplicarLimite(txtCidade, 50);
        TextFieldUtils.aplicarLimite(txtBairro, 50);
        MaskUtils.aplicarMascaraCEP(txtCep);
        TextFieldUtils.aplicarLimite(txtLogradouro, 80);
        TextFieldUtils.aplicarLimite(txtNumero, 8);
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

        if (cliente.getEndereco() != null) {
            txtCidade.setText(cliente.getEndereco().getCidade());
            txtBairro.setText(cliente.getEndereco().getBairro());
            txtCep.setText(cliente.getEndereco().getCep());
            txtLogradouro.setText(cliente.getEndereco().getLogradouro());
            txtNumero.setText(cliente.getEndereco().getNumero() != null
                    ? cliente.getEndereco().getNumero().toString()
                    : "");
            comboEstado.setValue(cliente.getEndereco().getEstado());
        } else {
            limparCamposEndereco();
        }
    }

    @FXML
    private void salvar() {
        var data = new ClienteFormData(
                txtNome.getText(), txtTelefone.getText(), txtEmail.getText(),
                txtCidade.getText(), txtBairro.getText(), txtCep.getText(),
                txtLogradouro.getText(), parseInteger(txtNumero), comboEstado.getValue()
        );

        boolean sucesso = salvarAction.execute(novoCliente, data);
        if (sucesso) voltar();
    }

    @FXML
    private void excluir() {
        boolean sucesso = excluirAction.execute(novoCliente, null);
        if (sucesso) voltar();
    }

    @FXML
    private void voltar() {
        navigator.openViewRootPane("/fxml/cliente/cliente-gerenciar.fxml", rootPane, null);
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
}
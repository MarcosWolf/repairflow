package com.marcoswolf.crm.reparos.ui.controller.tipoEquipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.ui.handler.cliente.ClienteFormData;
import com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.TipoEquipamentoExcluirAction;
import com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.TipoEquipamentoFormData;
import com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.TipoEquipamentoSalvarAction;
import com.marcoswolf.crm.reparos.ui.interfaces.DataReceiver;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import com.marcoswolf.crm.reparos.ui.utils.TextFieldUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
public class TipoEquipamentoFormController implements DataReceiver<TipoEquipamento> {
    private final ViewNavigator navigator;
    private final TipoEquipamentoSalvarAction salvarAction;
    private final TipoEquipamentoExcluirAction excluirAction;
    private TipoEquipamento tipoEquipamentoEditando;

    @FXML private AnchorPane rootPane;
    @FXML private Label lblTitulo;
    @FXML private TextField txtNome;
    @FXML private Button btnExcluir;

    @FXML
    public void initialize() {
        configurarCampos();
    }

    private void configurarCampos() {
        TextFieldUtils.aplicarLimite(txtNome, 50);
    }

    @Override
    public void setData(TipoEquipamento tipoEquipamento) {
        this.tipoEquipamentoEditando = tipoEquipamento;
        preencherFormulario(tipoEquipamento);
    }

    private void preencherFormulario(TipoEquipamento tipoEquipamento) {
        if (tipoEquipamento == null) {
            lblTitulo.setText("Cadastrar Tipo de Equipamento");
            btnExcluir.setVisible(false);
            limparCampos();
            return;
        }

        lblTitulo.setText("Editar Tipo de Equipamento");
        btnExcluir.setVisible(true);

        txtNome.setText(tipoEquipamento.getNome());
    }

    @FXML
    private void onSalvar() {
        var data = new TipoEquipamentoFormData(
                txtNome.getText()
        );

        boolean sucesso = salvarAction.execute(tipoEquipamentoEditando, data);
        if (sucesso) onVoltar();
    }

    @FXML
    private void onExcluir() {
        boolean sucesso = excluirAction.execute(tipoEquipamentoEditando, null);
        if (sucesso) onVoltar();
    }

    @FXML
    private void onVoltar() {
        navigator.openViewRootPane("/fxml/tipoEquipamento/tipoEquipamento-gerenciar.fxml", rootPane, null);
    }

    private void limparCampos() {
        txtNome.clear();
    }
}

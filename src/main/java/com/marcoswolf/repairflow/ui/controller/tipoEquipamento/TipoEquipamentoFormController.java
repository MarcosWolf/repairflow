package com.marcoswolf.repairflow.ui.controller.tipoEquipamento;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.action.TipoEquipamentoExcluirAction;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.action.TipoEquipamentoSalvarAction;
import com.marcoswolf.repairflow.ui.interfaces.DataReceiver;
import com.marcoswolf.repairflow.ui.navigation.ViewNavigator;
import com.marcoswolf.repairflow.ui.utils.TextFieldUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TipoEquipamentoFormController implements DataReceiver<TipoEquipamento> {
    private final ViewNavigator navigator;
    private final TipoEquipamentoSalvarAction salvarAction;
    private final TipoEquipamentoExcluirAction excluirAction;

    private TipoEquipamento novoTipoEquipamento;

    @FXML private AnchorPane rootPane;
    @FXML private Label lblTitulo;
    @FXML private TextField txtNome;
    @FXML private Button btnExcluir;

    private static final String GERENCIAR_PATH = "/fxml/tipoEquipamento/tipoEquipamento-gerenciar.fxml";

    @FXML
    public void initialize() {
        configurarCampos();
    }

    private void configurarCampos() {
        TextFieldUtils.aplicarLimite(txtNome, 50);
    }

    @Override
    public void setData(TipoEquipamento tipoEquipamento) {
        this.novoTipoEquipamento = tipoEquipamento;
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

    private void limparCampos() {
        txtNome.clear();
    }

    @FXML
    private void salvar() {
        boolean sucesso = salvarAction.execute(novoTipoEquipamento, criarFormData());
        if (sucesso) voltar();
    }

    @FXML
    private void excluir() {
        boolean sucesso = excluirAction.execute(novoTipoEquipamento, null);
        if (sucesso) voltar();
    }

    @FXML
    private void voltar() {
        navigator.openViewRootPane(GERENCIAR_PATH, rootPane, null);
    }

    private TipoEquipamentoFormData criarFormData() {
        return new TipoEquipamentoFormData(
                txtNome.getText()
        );
    }
}
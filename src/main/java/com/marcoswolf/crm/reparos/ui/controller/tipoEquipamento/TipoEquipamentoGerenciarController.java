package com.marcoswolf.crm.reparos.ui.controller.tipoEquipamento;

import com.marcoswolf.crm.reparos.business.tipoEquipamento.TipoEquipamentoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
import com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.action.TipoEquipamentoBuscarAction;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import com.marcoswolf.crm.reparos.ui.tables.TipoEquipamentoTableView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TipoEquipamentoGerenciarController {
    private final MainViewController mainViewController;
    private final ViewNavigator navigator;

    private final TipoEquipamentoService tipoEquipamentoService;

    private final TipoEquipamentoBuscarAction buscarAction;

    @FXML private AnchorPane rootPane;
    @FXML private TextField txtBuscar;
    @FXML private TableView<TipoEquipamento> tabela;
    @FXML private TableColumn<TipoEquipamento, String> colNome;
    @FXML private TableColumn<TipoEquipamento, Number> colTotalClientes;
    @FXML private TableColumn<TipoEquipamento, Number> colTotalEquipamentos;
    @FXML private TableColumn<TipoEquipamento, Number> colTotalReparos;

    private boolean filtrosVisiveis = false;

    private static final String FORM_PATH = "/fxml/tipoEquipamento/tipoEquipamento-form.fxml";

    @FXML
    private void initialize() {
        TipoEquipamentoTableView.configurarTabela(tabela, colNome, colTotalClientes, colTotalEquipamentos, colTotalReparos, this::editar);
        alimentarTabela();
    }

    private void alimentarTabela() {
        tabela.setItems(FXCollections.observableList(tipoEquipamentoService.listarTodosTabela()));
    }

    @FXML
    private void voltar() {
        ((AnchorPane) rootPane.getParent()).getChildren().remove(rootPane);
    }

    @FXML
    public void buscar() {
        tabela.setItems(FXCollections.observableList(buscarAction.executar(txtBuscar.getText())));
    }

    @FXML
    public void cadastrar() {
        navigator.openView(FORM_PATH, mainViewController.getContentArea(), null);
    }

    @FXML
    public void editar(TipoEquipamento tipoEquipamento) {
        navigator.openView(FORM_PATH, mainViewController.getContentArea(), tipoEquipamento);
    }
}

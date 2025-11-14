package com.marcoswolf.crm.reparos.ui.controller.equipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
import com.marcoswolf.crm.reparos.ui.handler.equipamento.action.EquipamentoBuscarAction;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import com.marcoswolf.crm.reparos.ui.tables.EquipamentoTableView;
import com.marcoswolf.crm.reparos.ui.utils.TableUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class EquipamentoGerenciarController {
    private final MainViewController mainViewController;
    private final ViewNavigator navigator;

    private final EquipamentoBuscarAction buscarAction;

    @FXML
    private AnchorPane rootPane;
    @FXML private TextField txtBuscar;
    @FXML private TableView<Equipamento> tabela;
    @FXML private TableColumn<Equipamento, String> colMarca;
    @FXML private TableColumn<Equipamento, String> colModelo;

    private boolean filtrosVisiveis = false;

    private static final String FORM_PATH = "/fxml/equipamento/equipamento-form.fxml";

    @FXML
    private void initialize() {
        EquipamentoTableView.configurarTabela(tabela, colMarca, colModelo, this::editar);
        alimentarTabela();
    }

    private void alimentarTabela() {
        tabela.setItems(FXCollections.observableList(buscarAction.executar("")));
    }

    @FXML
    private void voltar() {
        navigator.closeCurrentView(rootPane);
    }

    @FXML
    public void buscar() {
        tabela.setItems(FXCollections.observableList(buscarAction.executar(txtBuscar.getText())));
    }

    @FXML
    public void cadastrar() {
        navigator.openView(FORM_PATH, mainViewController.getContentArea(), null);
    }

    private void editar(Equipamento equipamento) {
        navigator.openView(FORM_PATH, mainViewController.getContentArea(), equipamento);
    }
}

package com.marcoswolf.crm.reparos.ui.controller.reparo;

import com.marcoswolf.crm.reparos.business.reparo.ReparoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
import com.marcoswolf.crm.reparos.ui.handler.reparo.action.ReparoBuscarAction;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import com.marcoswolf.crm.reparos.ui.tables.ReparoTableView;
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
public class ReparoGerenciarController {
    private final MainViewController mainViewController;
    private final ViewNavigator navigator;

    private final ReparoService reparoService;

    private final ReparoBuscarAction buscarAction;

    @FXML private AnchorPane rootPane;
    @FXML private TextField txtBuscar;
    @FXML private TableView<Reparo> tabela;
    @FXML private TableColumn<Reparo, String> colNome;

    private boolean filtrosVisiveis = false;

    private static final String FORM_PATH = "/fxml/reparo/reparo-form.fxml";

    @FXML
    private void initialize() {
        ReparoTableView.configurarTabela(tabela, colNome, this::editar);
        alimentarTabela();
    }

    private void alimentarTabela() {
        tabela.setItems(FXCollections.observableList(reparoService.listarTodos()));
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
    public void editar(Reparo reparo) {
        navigator.openView(FORM_PATH, mainViewController.getContentArea(), reparo);
    }
}

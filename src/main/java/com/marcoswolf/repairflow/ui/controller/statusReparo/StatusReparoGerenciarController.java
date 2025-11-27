package com.marcoswolf.repairflow.ui.controller.statusReparo;

import com.marcoswolf.repairflow.business.statusReparo.StatusReparoService;
import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import com.marcoswolf.repairflow.ui.controller.MainViewController;
import com.marcoswolf.repairflow.ui.handler.statusReparo.action.StatusReparoBuscarAction;
import com.marcoswolf.repairflow.ui.navigation.ViewNavigator;
import com.marcoswolf.repairflow.ui.tables.StatusReparoTableView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StatusReparoGerenciarController {
    private final MainViewController mainViewController;
    private final ViewNavigator navigator;

    private final StatusReparoService statusReparoService;

    private final StatusReparoBuscarAction buscarAction;

    @FXML private AnchorPane rootPane;
    @FXML private TextField txtBuscar;
    @FXML private TableView<StatusReparo> tabela;
    @FXML private TableColumn<StatusReparo, String> colNome;
    @FXML private TableColumn<StatusReparo, Number> colTotalReparos;

    private boolean filtrosVisiveis = false;

    private static final String FORM_PATH = "/fxml/statusReparo/statusReparo-form.fxml";

    @FXML
    private void initialize() {
        StatusReparoTableView.configurarTabela(tabela, colNome, colTotalReparos, this::editar);
        alimentarTabela();
    }

    private void alimentarTabela() {
        List<StatusReparo> statusReparos = statusReparoService.listarTodos();

        statusReparos.forEach(t -> {
            t.setTotalReparos(statusReparoService.contarReparosPorStatusReparo(t.getId()));
        });

        tabela.setItems(FXCollections.observableList(statusReparos));
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
        navigator.openView(FORM_PATH, mainViewController.getContentArea(),null);
    }

    @FXML
    public void editar(StatusReparo statusReparo) {
        navigator.openView(FORM_PATH, mainViewController.getContentArea(), statusReparo);
    }
}
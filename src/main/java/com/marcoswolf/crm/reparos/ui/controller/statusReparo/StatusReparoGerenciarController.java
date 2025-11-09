package com.marcoswolf.crm.reparos.ui.controller.statusReparo;

import com.marcoswolf.crm.reparos.business.statusReparo.StatusReparoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.StatusReparo;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
import com.marcoswolf.crm.reparos.ui.handler.statusReparo.StatusReparoBuscarAction;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import com.marcoswolf.crm.reparos.ui.utils.TableUtils;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StatusReparoGerenciarController {
    private final StatusReparoService statusReparoService;
    private final MainViewController mainViewController;
    private final ViewNavigator navigator;

    @FXML private AnchorPane rootPane;

    private final StatusReparoBuscarAction buscarAction;

    @FXML private TextField txtBuscar;
    @FXML private TableView<StatusReparo> tabela;

    @FXML private TableColumn<StatusReparo, String> colNome;
    @FXML private TableColumn<StatusReparo, Number> colTotalReparos;

    public static final String FORM_PATH = "/fxml/statusReparo/statusReparo-form.fxml";

    @FXML
    private void initialize() {
        configurarTabela();
    }

    private void configurarTabela() {
        instanciarTabela();
        alimentarTabela();

        TableUtils.setDoubleClickAction(tabela, itemSelecionado -> {
            editar(itemSelecionado);
        });

        centralizarColunas();
    }

    private void instanciarTabela() {
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        colTotalReparos.setCellValueFactory(c -> new SimpleLongProperty(
                Optional.ofNullable(c.getValue().getTotalReparos()).orElse(0L)
        ));
    }

    @FXML
    private void voltar() {
        ((AnchorPane) rootPane.getParent()).getChildren().remove(rootPane);
    }

    private void alimentarTabela() {
        List<StatusReparo> statusReparos = statusReparoService.listarTodos();

        statusReparos.forEach(t -> {
            t.setTotalReparos(statusReparoService.contarReparosPorStatusReparo(t.getId()));
        });

        tabela.setItems(FXCollections.observableList(statusReparos));
    }

    private void centralizarColunas() {
        TableUtils.centralizarColuna(colTotalReparos);
    }

    @FXML
    public void buscar() {
        var nome = txtBuscar.getText();
        var statusReparos = buscarAction.executar(nome);
        tabela.setItems(FXCollections.observableList(statusReparos));
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

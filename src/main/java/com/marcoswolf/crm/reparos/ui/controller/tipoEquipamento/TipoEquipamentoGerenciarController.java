package com.marcoswolf.crm.reparos.ui.controller.tipoEquipamento;

import com.marcoswolf.crm.reparos.business.tipoEquipamento.TipoEquipamentoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.ui.config.SpringFXMLLoader;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
import com.marcoswolf.crm.reparos.ui.handler.cliente.ClienteBuscarAction;
import com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.TipoEquipamentoBuscarAction;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TipoEquipamentoGerenciarController {
    private final TipoEquipamentoService tipoEquipamentoService;
    private final SpringFXMLLoader fxmlLoader;
    private final MainViewController mainViewController;
    private final ViewNavigator navigator;

    @FXML private AnchorPane rootPane;

    private final TipoEquipamentoBuscarAction buscarAction;

    @FXML private TextField txtBuscar;
    @FXML private TableView<TipoEquipamento> tabelaTipoEquipamento;
    @FXML private TableColumn<TipoEquipamento, String> colNome;

    @FXML
    private void initialize() {
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));

        carregarTipoEquipamentos();

        tabelaTipoEquipamento.setRowFactory(tv -> {
            TableRow<TipoEquipamento> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    TipoEquipamento tipoEquipamentoSelecionado = row.getItem();
                    System.out.println("Editando o usuário " + row.getItem());
                    abrirTelaEdicao(tipoEquipamentoSelecionado);
                }
            });
            return row;
        });
    }

    @FXML
    private void onVoltar() {
        ((AnchorPane) rootPane.getParent()).getChildren().remove(rootPane);
    }

    private void carregarTipoEquipamentos() {
        List<TipoEquipamento> tipoEquipamentos = tipoEquipamentoService.listarTodos();
        tabelaTipoEquipamento.setItems(FXCollections.observableList(tipoEquipamentos));
    }

    @FXML
    public void onBuscar() {
        var nome = txtBuscar.getText();
        var tipoEquipamentos = buscarAction.executar(nome);
        tabelaTipoEquipamento.setItems(FXCollections.observableList(tipoEquipamentos));
    }

    @FXML
    public void onNovo(ActionEvent actionEvent) {
        navigator.openView("/fxml/tipoEquipamento/tipoEquipamento-form.fxml",
                mainViewController.getContentArea(),
                null);
    }

    @FXML
    public void abrirTelaEdicao(TipoEquipamento tipoEquipamento) {
        navigator.openView("/fxml/tipoEquipamento/tipoEquipamento-form.fxml",
                mainViewController.getContentArea(),
                tipoEquipamento);
    }
}

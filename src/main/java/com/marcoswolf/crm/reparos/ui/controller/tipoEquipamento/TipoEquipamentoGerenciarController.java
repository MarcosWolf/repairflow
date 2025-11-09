package com.marcoswolf.crm.reparos.ui.controller.tipoEquipamento;

import com.marcoswolf.crm.reparos.business.tipoEquipamento.TipoEquipamentoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
import com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.TipoEquipamentoBuscarAction;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import com.marcoswolf.crm.reparos.ui.utils.TableUtils;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TipoEquipamentoGerenciarController {
    private final TipoEquipamentoService tipoEquipamentoService;
    private final MainViewController mainViewController;
    private final ViewNavigator navigator;

    @FXML private AnchorPane rootPane;

    private final TipoEquipamentoBuscarAction buscarAction;

    @FXML private TextField txtBuscar;
    @FXML private TableView<TipoEquipamento> tabela;

    @FXML private TableColumn<TipoEquipamento, String> colNome;
    @FXML private TableColumn<TipoEquipamento, Number> colTotalClientes;
    @FXML private TableColumn<TipoEquipamento, Number> colTotalEquipamentos;
    @FXML private TableColumn<TipoEquipamento, Number> colTotalReparos;

    public static final String FORM_PATH = "/fxml/tipoEquipamento/tipoEquipamento-form.fxml";

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
        colTotalClientes.setCellValueFactory(c -> new SimpleLongProperty(
                Optional.ofNullable(c.getValue().getTotalClientes()).orElse(0L)
        ));
        colTotalEquipamentos.setCellValueFactory(c -> new SimpleLongProperty(
                Optional.ofNullable(c.getValue().getTotalEquipamentos()).orElse(0L)
        ));
        colTotalReparos.setCellValueFactory(c -> new SimpleLongProperty(
                Optional.ofNullable(c.getValue().getTotalReparos()).orElse(0L)
        ));
    }

    @FXML
    private void voltar() {
        ((AnchorPane) rootPane.getParent()).getChildren().remove(rootPane);
    }

    private void alimentarTabela() {
        List<TipoEquipamento> tipoEquipamentos = tipoEquipamentoService.listarTodos();

        tipoEquipamentos.forEach(t -> {
            t.setTotalClientes(tipoEquipamentoService.contarClientesPorTipo(t.getId()));
            t.setTotalEquipamentos(tipoEquipamentoService.contarEquipamentosPorTipo(t.getId()));
            t.setTotalReparos(tipoEquipamentoService.contarReparosPorTipo(t.getId()));
        });

        tabela.setItems(FXCollections.observableList(tipoEquipamentos));
    }

    private void centralizarColunas() {
        TableUtils.centralizarColuna(colTotalClientes);
        TableUtils.centralizarColuna(colTotalEquipamentos);
        TableUtils.centralizarColuna(colTotalReparos);
    }

    @FXML
    public void buscar() {
        var nome = txtBuscar.getText();
        var tipoEquipamentos = buscarAction.executar(nome);
        tabela.setItems(FXCollections.observableList(tipoEquipamentos));
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

package com.marcoswolf.repairflow.ui.controller.cliente;


import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.ui.controller.MainViewController;
import com.marcoswolf.repairflow.ui.handler.cliente.action.ClienteBuscarAction;
import com.marcoswolf.repairflow.ui.handler.cliente.action.ClienteFiltrarAction;
import com.marcoswolf.repairflow.ui.handler.cliente.action.ClienteLimparFiltrosAction;
import com.marcoswolf.repairflow.ui.handler.cliente.action.ClienteToggleFiltrosAction;
import com.marcoswolf.repairflow.ui.handler.cliente.dto.ClienteFiltroDTO;
import com.marcoswolf.repairflow.ui.navigation.ViewNavigator;
import com.marcoswolf.repairflow.ui.tables.ClienteTableView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ClienteGerenciarController {
    private final MainViewController mainViewController;
    private final ViewNavigator navigator;

    private final ClienteBuscarAction buscarAction;
    private final ClienteFiltrarAction filtrarAction;
    private final ClienteLimparFiltrosAction limparFiltrosAction;
    private final ClienteToggleFiltrosAction toggleFiltrosAction;

    @FXML private AnchorPane rootPane;
    @FXML private VBox filtroPane;
    @FXML private TextField txtBuscar;
    @FXML private TableView<Cliente> tabela;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colCidade;
    @FXML private TableColumn<Cliente, String> colEstado;
    @FXML private CheckBox chkPendentes;
    @FXML private CheckBox chkInativos;
    @FXML private CheckBox chkRecentes;

    private boolean filtrosVisiveis = false;

    private static final String FORM_PATH = "/fxml/cliente/cliente-form.fxml";

    @FXML
    private void initialize() {
        ClienteTableView.configurarTabela(tabela, colNome, colTelefone, colCidade, colEstado, this::editar);
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

    private void editar(Cliente cliente) {
        navigator.openView(FORM_PATH, mainViewController.getContentArea(), cliente);
    }

    @FXML
    public void toggleFiltros() {
        filtrosVisiveis = toggleFiltrosAction.executar(filtrosVisiveis, filtroPane);
    }

    @FXML
    public void aplicarFiltros() {
        var filtro = new ClienteFiltroDTO(
                txtBuscar.getText(),
                chkPendentes.isSelected(),
                chkInativos.isSelected(),
                chkRecentes.isSelected(),
                LocalDate.now().minusDays(30)
        );

        tabela.setItems(FXCollections.observableList(filtrarAction.executar(filtro)));
        filtrosVisiveis = toggleFiltrosAction.executar(filtrosVisiveis, filtroPane);
    }

    @FXML
    public void limparFiltros() {
        limparFiltrosAction.executar(chkPendentes, chkInativos, chkRecentes, txtBuscar, tabela);
        alimentarTabela();
    }


}
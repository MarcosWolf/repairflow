package com.marcoswolf.crm.reparos.ui.controller.cliente;


import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
import com.marcoswolf.crm.reparos.ui.handler.cliente.*;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import com.marcoswolf.crm.reparos.ui.utils.TableUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Scope("prototype")
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
    @FXML private CheckBox chkReparosAberto;
    @FXML private CheckBox chkRecentes;

    private boolean filtrosVisiveis = false;

    @FXML
    private void initialize() {
        configurarTabela();
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        colTelefone.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefone()));
        colCidade.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getEndereco() != null ? c.getValue().getEndereco().getCidade() : ""
        ));
        colEstado.setCellValueFactory(c -> {
            var endereco = c.getValue().getEndereco();
            return new SimpleStringProperty(
                    endereco != null && endereco.getEstado() != null ? endereco.getEstado().getNome() : ""
            );
        });

        carregarClientesIniciais();

        TableUtils.setDoubleClickAction(tabela, statusSelecionado -> {
            editar(statusSelecionado);
        });

        centralizarColunas();
    }

    private void carregarClientesIniciais() {
        var clientes = buscarAction.executar("");
        tabela.setItems(FXCollections.observableList(clientes));
    }

    private void centralizarColunas() {
        TableUtils.centralizarColuna(colCidade);
        TableUtils.centralizarColuna(colEstado);
        TableUtils.centralizarColuna(colTelefone);
    }

    @FXML
    private void voltar() {
        navigator.closeCurrentView(rootPane);
    }

    @FXML
    public void buscar() {
        var nome = txtBuscar.getText();
        var clientes = buscarAction.executar(nome);
        tabela.setItems(FXCollections.observableList(clientes));
    }

    @FXML
    public void cadastrar() {
        navigator.openView("/fxml/cliente/cliente-form.fxml",
                mainViewController.getContentArea(), null);
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
                chkReparosAberto.isSelected(),
                chkInativos.isSelected(),
                chkRecentes.isSelected(),
                LocalDate.now().minusDays(30)
        );

        var clientes = filtrarAction.executar(filtro);
        tabela.setItems(FXCollections.observableList(clientes));
        filtrosVisiveis = toggleFiltrosAction.executar(filtrosVisiveis, filtroPane);
    }

    @FXML
    public void limparFiltros() {
        limparFiltrosAction.executar(chkPendentes, chkReparosAberto, chkInativos, chkRecentes, txtBuscar, tabela);
        carregarClientesIniciais();
    }

    private void editar(Cliente cliente) {
        navigator.openView("/fxml/cliente/cliente-form.fxml",
                mainViewController.getContentArea(), cliente);
    }
}
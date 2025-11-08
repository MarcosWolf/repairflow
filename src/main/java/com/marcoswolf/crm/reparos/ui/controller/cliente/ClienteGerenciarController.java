package com.marcoswolf.crm.reparos.ui.controller.cliente;

import com.marcoswolf.crm.reparos.business.cliente.ClienteFiltro;
import com.marcoswolf.crm.reparos.business.cliente.ClienteService;
import com.marcoswolf.crm.reparos.business.cliente.IClienteConsultaService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteGerenciarController {
    private final IClienteConsultaService clienteConsultaService;

    private final MainViewController mainViewController;
    private final ViewNavigator navigator;
    private final AlertService alertService;

    @FXML private AnchorPane rootPane;

    @FXML private VBox filtroPane;
    private boolean filtrosVisiveis = false;
    @FXML private CheckBox chkPendentes;
    @FXML private CheckBox chkInativos;
    @FXML private CheckBox chkReparosAberto;
    @FXML private CheckBox chkRecentes;


    @FXML private TextField txtBuscar;
    @FXML private TableView<Cliente> tabelaClientes;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colCidade;
    @FXML private TableColumn<Cliente, String> colEstado;

    @FXML
    private void initialize() {
        configurarTabela();
        carregarClientes();
        configurarDuploClique();
    }

    private void carregarClientes() {
        try {
            var clientes = clienteConsultaService.buscarPorNome("");
            tabelaClientes.setItems(FXCollections.observableList(clientes));
        } catch (Exception e) {
            alertService.error("Erro", "Não foi possível carregar os clientes.");
        }
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        colTelefone.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefone()));
        colCidade.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getEndereco() != null ? c.getValue().getEndereco().getCidade() : "")
        );
        colEstado.setCellValueFactory(c -> {
            var endereco = c.getValue().getEndereco();
            return new SimpleStringProperty(
                    endereco != null && endereco.getEstado() != null ? endereco.getEstado().getNome() : ""
            );
        });
    }

    private void configurarDuploClique() {
        tabelaClientes.setRowFactory(tv -> {
            TableRow<Cliente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    abrirTelaEdicao(row.getItem());
                }
            });
            return row;
        });
    }

    @FXML
    private void onVoltar() {
        navigator.closeCurrentView(rootPane);
    }

    @FXML
    public void onBuscar() {
        try {
            var nome = txtBuscar.getText();
            var clientes = clienteConsultaService.buscarPorNome(nome);
            tabelaClientes.setItems(FXCollections.observableList(clientes));
        } catch (Exception e) {
            alertService.error("Erro", "Falha ao buscar clientes.");
        }
    }

    @FXML
    public void onNovo() {
        navigator.openView("/fxml/cliente/cliente-form.fxml",
                mainViewController.getContentArea(), null);
    }

    @FXML
    public void abrirTelaEdicao(Cliente cliente) {
        navigator.openView("/fxml/cliente/cliente-form.fxml",
                mainViewController.getContentArea(), cliente);
    }

    @FXML
    public void onToggleFiltros() {
        filtrosVisiveis = !filtrosVisiveis;

        if (filtrosVisiveis) {
            filtroPane.setVisible(true);
            filtroPane.setManaged(true);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), filtroPane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            TranslateTransition slideDown = new TranslateTransition(Duration.millis(200), filtroPane);
            slideDown.setFromY(-10);
            slideDown.setToY(0);

            fadeIn.play();
            slideDown.play();
        } else {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), filtroPane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            TranslateTransition slideUp = new TranslateTransition(Duration.millis(200), filtroPane);
            slideUp.setFromY(0);
            slideUp.setToY(-10);
            fadeOut.setOnFinished(e -> {
                filtroPane.setVisible(false);
                filtroPane.setManaged(false);
            });
            fadeOut.play();
            slideUp.play();
        }
    }

    @FXML
    public void onAplicarFiltros() {
        try {
            var filtro = new ClienteFiltro();
            filtro.setNome(txtBuscar.getText());
            filtro.setPendentes(chkPendentes.isSelected());
            filtro.setComReparos(chkReparosAberto.isSelected());
            filtro.setInativos(chkInativos.isSelected());
            filtro.setRecentes(chkRecentes.isSelected());

            var clientes = clienteConsultaService.filtrarClientes(filtro);
            tabelaClientes.setItems(FXCollections.observableList(clientes));
            onToggleFiltros();

        } catch (Exception e) {
            alertService.error("Erro", "Falha ao aplicar filtros: " + e.getMessage());
        }
    }

    @FXML
    public void onLimparFiltros() {
        chkPendentes.setSelected(false);
        chkReparosAberto.setSelected(false);
        chkRecentes.setSelected(false);
        chkInativos.setSelected(false);
        txtBuscar.clear();
        carregarClientes();
    }
}

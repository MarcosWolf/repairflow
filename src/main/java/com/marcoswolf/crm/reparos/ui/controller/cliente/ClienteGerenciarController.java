package com.marcoswolf.crm.reparos.ui.controller.cliente;

import com.marcoswolf.crm.reparos.business.cliente.ClienteService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.config.SpringFXMLLoader;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
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

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClienteGerenciarController {
    private final ClienteService clienteService;
    private final SpringFXMLLoader fxmlLoader;
    private final MainViewController mainViewController;

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
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
        colTelefone.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTelefone()));
        colCidade.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getEndereco() != null ? c.getValue().getEndereco().getCidade() : ""));
        colEstado.setCellValueFactory(c -> {
            var endereco = c.getValue().getEndereco();
            var nomeEstado = (endereco != null && endereco.getEstado() != null)
                    ? endereco.getEstado().getNome()
                    : "";
            return new SimpleStringProperty(nomeEstado);
        });

        carregarClientes();

        tabelaClientes.setRowFactory(tv -> {
            TableRow<Cliente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Cliente clienteSelecionado = row.getItem();
                    System.out.println("Editando o usuário " + row.getItem());
                    abrirTelaEdicao(clienteSelecionado);
                }
            });
            return row;
        });
    }

    @FXML
    private void onVoltar() {
        ((AnchorPane) rootPane.getParent()).getChildren().remove(rootPane);
    }

    private void carregarClientes() {
        List<Cliente> clientes = clienteService.buscarPorNome("");
        tabelaClientes.setItems(FXCollections.observableList(clientes));
    }

    @FXML
    public void onBuscar() {
        String nome = txtBuscar.getText();
        try {
            List<Cliente> clientes = clienteService.buscarPorNome(nome);
            tabelaClientes.setItems(FXCollections.observableList(clientes));
        } catch (Exception e) {
            tabelaClientes.getItems().clear();
        }
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
            var filtro = new com.marcoswolf.crm.reparos.business.cliente.ClienteFiltro();
            filtro.setNome(txtBuscar.getText());
            filtro.setPendentes(chkPendentes.isSelected());
            filtro.setComReparos(chkReparosAberto.isSelected());
            filtro.setInativos(chkInativos.isSelected());
            filtro.setRecentes(chkRecentes.isSelected());

            var clientesFiltrados = clienteService.filtrarClientes(filtro);
            tabelaClientes.setItems(FXCollections.observableList(clientesFiltrados));
            onToggleFiltros();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao aplicar filtros: " + e.getMessage()).showAndWait();
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

    @FXML
    public void onNovo() {
        mainViewController.abrirTela("/fxml/cliente/cliente-form.fxml", null);
    }

    @FXML
    public void abrirTelaEdicao(Cliente cliente) {
        mainViewController.abrirTela("/fxml/cliente/cliente-form.fxml", cliente);
    }
}

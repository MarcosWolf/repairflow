package com.marcoswolf.crm.reparos.ui.controller.cliente;

import com.marcoswolf.crm.reparos.business.ClienteService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.config.SpringFXMLLoader;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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

    @FXML private TextField txtBuscar;
    @FXML private TableView<Cliente> tabelaClientes;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colCidade;
    @FXML private TableColumn<Cliente, String> colEstado;
    @FXML private TableColumn<Cliente, Void> colAcoes;

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
    public void onBuscar(ActionEvent actionEvent) {
        String nome = txtBuscar.getText();
        try {
            List<Cliente> clientes = clienteService.buscarPorNome(nome);
            tabelaClientes.setItems(FXCollections.observableList(clientes));
        } catch (Exception e) {
            tabelaClientes.getItems().clear();
        }
    }

    @FXML
    public void onNovo(ActionEvent actionEvent) {
        mainViewController.abrirTela("/fxml/cliente/cliente-form.fxml", null);
    }

    @FXML
    public void abrirTelaEdicao(Cliente cliente) {
        mainViewController.abrirTela("/fxml/cliente/cliente-form.fxml", cliente);
    }


}

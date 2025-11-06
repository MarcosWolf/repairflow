package com.marcoswolf.crm.reparos.ui.controller.cliente;

import com.marcoswolf.crm.reparos.business.ClienteService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.config.SpringFXMLLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClienteGerenciarController {
    private final ClienteService clienteService;
    private final SpringFXMLLoader fxmlLoader;

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

        adicionarColunaAcoes();
        carregarClientes();
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

    }

    private void adicionarColunaAcoes() {
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(5, btnEditar, btnExcluir);

            {
                btnEditar.setOnAction(e -> editarCliente(getTableView().getItems().get(getIndex())));
                btnExcluir.setOnAction(e -> excluirCliente(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else setGraphic(box);
            }
        });
    }

    private void editarCliente(Cliente cliente) {
        // Aqui você pode abrir o cliente-form.fxml com o cliente selecionado
        // e preencher os campos (modo edição)
    }

    private void excluirCliente(Cliente cliente) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente excluir " + cliente.getNome() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(res -> {
            if (res == ButtonType.YES) {
                try {
                    clienteService.deletarCliente(cliente.getId());
                    carregarClientes();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
                }
            }
        });
    }
}

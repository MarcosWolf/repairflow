package com.marcoswolf.crm.reparos.ui.controller.cliente;

import com.marcoswolf.crm.reparos.business.cliente.ClienteService;
import com.marcoswolf.crm.reparos.business.estado.EstadoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import com.marcoswolf.crm.reparos.ui.BaseUITest;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import com.marcoswolf.crm.reparos.ui.handler.cliente.action.ClienteBuscarAction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class ClienteGerenciarControllerTest extends BaseUITest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AlertService alertService;

    @MockitoBean
    private ClienteService clienteService;

    @MockitoBean
    private EstadoService estadoService;

    @MockitoBean
    private ClienteBuscarAction buscarAction;

    private ClienteGerenciarController controller;
    private MainViewController mainController;

    private List<Estado> criarEstadosMock() {
        return Arrays.asList(
                Estado.builder().id(1L).nome("São Paulo").build(),
                Estado.builder().id(2L).nome("Rio de Janeiro").build(),
                Estado.builder().id(3L).nome("Minas Gerais").build(),
                Estado.builder().id(4L).nome("Bahia").build()
        );
    }

    @Override
    public void start(Stage stage) throws Exception {
        alertService.setTestMode(true);

        List<Estado> estadosMock = criarEstadosMock();
        when(estadoService.listarTodos()).thenReturn(estadosMock);

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/fxml/main-view.fxml"));
        mainLoader.setControllerFactory(applicationContext::getBean);

        Scene scene = new Scene(mainLoader.load(), 1024, 768);
        stage.setScene(scene);
        stage.show();

        mainController = mainLoader.getController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cliente/cliente-gerenciar.fxml"));
        loader.setControllerFactory(applicationContext::getBean);

        Parent clienteView = loader.load();

        mainController.getContentArea().getChildren().clear();
        mainController.getContentArea().getChildren().add(clienteView);

        AnchorPane.setTopAnchor(clienteView, 0.0);
        AnchorPane.setBottomAnchor(clienteView, 0.0);
        AnchorPane.setLeftAnchor(clienteView, 0.0);
        AnchorPane.setRightAnchor(clienteView, 0.0);

        this.controller = loader.getController();

        waitForFxEvents();
    }

    @Test
    void deveCadastrarBuscarEEditarCliente() {
        List<Cliente> clientesMock = new ArrayList<>();
        ObservableList<Cliente> clientesObs = FXCollections.observableArrayList(clientesMock);

        doAnswer(invocation -> {
            Cliente cli = invocation.getArgument(0);
            cli.setId((long) (clientesMock.size() + 1));
            clientesMock.add(cli);
            clientesObs.setAll(clientesMock);
            return null;
        }).when(clienteService).salvar(any());

        when(buscarAction.executar("")).thenAnswer(invocation -> FXCollections.observableArrayList(clientesMock));

        clickOn("#btnNovo");
        waitForFxEvents();

        var txtNome = findNode("#txtNome", TextField.class);
        var txtTelefone = findNode("#txtTelefone", TextField.class);
        var txtCidade = findNode("#txtCidade", TextField.class);
        var cmbEstado = findNode("#comboEstado", ComboBox.class);

        interact(() -> {
            txtNome.setText("João Teste");
            txtTelefone.setText("11999999999");
            txtCidade.setText("São Paulo");
            if (!cmbEstado.getItems().isEmpty()) {
                cmbEstado.getSelectionModel().select(1); // Seleciona o primeiro estado
            }
        });

        clickOn("#btnSalvar");
        waitForFxEvents();

        TableView<Cliente> tabela = findNode("#tabela", TableView.class);
        assertThat(tabela.getItems()).hasSize(1);
        assertThat(tabela.getItems().get(0).getNome()).isEqualTo("João Teste");

        interact(() -> tabela.getSelectionModel().select(0));
        doubleClickOn("João Teste");
        waitForFxEvents();

        var txtNomeEdicao = findNode("#txtNome", TextField.class);
        assertThat(txtNomeEdicao.getText()).isEqualTo("João Teste");

        var cmbEstadoEdicao = findNode("#comboEstado", ComboBox.class);
        assertThat(cmbEstadoEdicao.getValue()).isNotNull();
        assertThat(((Estado) cmbEstadoEdicao.getValue()).getNome()).isEqualTo("São Paulo");
    }
}
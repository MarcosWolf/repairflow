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
    void deveCarregarComponentesPrincipais() {
        assertNodeExists("#btnVoltar");

        assertNodeExists("#txtBuscar");
        assertNodeExists("#btnBuscar");
        assertNodeExists("#btnFiltros");
        assertNodeExists("#btnNovo");

        assertNodeExists("#filtroPane");
        assertNodeExists("#chkPendentes");
        assertNodeExists("#chkInativos");
        assertNodeExists("#chkRecentes");

        assertNodeExists("#tabela");
        assertNodeExists("#colNome");
        assertNodeExists("#colTelefone");
        assertNodeExists("#colCidade");
        assertNodeExists("#colEstado");
    }

    @Test
    void deveCadastrarBuscarEEditarCliente() {
        List<Cliente> clientesMock = new ArrayList<>();
        ObservableList<Cliente> clientesObs = FXCollections.observableArrayList(clientesMock);

        doAnswer(invocation -> {
            Cliente cli = invocation.getArgument(0);

            if (cli.getId() == null) {
                cli.setId((long) (clientesMock.size() + 1));
                clientesMock.add(cli);
            } else {
                for (Cliente existente : clientesMock) {
                    if (existente.getId().equals(cli.getId())) {
                        existente.setNome(cli.getNome());
                        existente.setTelefone(cli.getTelefone());
                        existente.setEndereco(cli.getEndereco());
                        break;
                    }
                }
            }

            return null;
        }).when(clienteService).salvar(any());

        when(buscarAction.executar(any())).thenAnswer(inv ->
                FXCollections.observableArrayList(clientesMock));

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
                cmbEstado.getSelectionModel().select(1);
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
        var txtTelefoneEdicao = findNode("#txtTelefone", TextField.class);
        var txtCidadeEdicao = findNode("#txtCidade", TextField.class);
        var cmbEstadoEdicao = findNode("#comboEstado", ComboBox.class);

        assertThat(txtNomeEdicao.getText()).isEqualTo("João Teste");
        assertThat(cmbEstadoEdicao.getValue()).isNotNull();
        assertThat(((Estado) cmbEstadoEdicao.getValue()).getNome()).isEqualTo("São Paulo");

        interact(() -> {
            txtNomeEdicao.setText("Marcos Vinícios");
            txtTelefoneEdicao.setText("(13) 98131-4531");
            txtCidadeEdicao.setText("Guarulhos");
            if (!cmbEstadoEdicao.getItems().isEmpty()) {
                cmbEstadoEdicao.getSelectionModel().select(1);
            }
        });

        clickOn("#btnSalvar");
        waitForFxEvents();

        assertThat(tabela.getItems()).hasSize(1);
        assertThat(tabela.getItems().get(0).getNome()).isEqualTo("Marcos Vinícios");
        assertThat(tabela.getItems().get(0).getTelefone()).isEqualTo("(13) 98131-4531");
        assertThat(tabela.getItems().get(0).getEndereco().getCidade()).isEqualTo("Guarulhos");
    }

    void deveCadastrarBuscarClientePorNome() {

    }
}
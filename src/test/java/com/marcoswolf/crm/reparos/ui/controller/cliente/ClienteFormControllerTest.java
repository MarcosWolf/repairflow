package com.marcoswolf.crm.reparos.ui.controller.cliente;


import com.marcoswolf.crm.reparos.business.estado.EstadoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import com.marcoswolf.crm.reparos.ui.BaseUITest;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.Hibernate.getClass;
import static org.mockito.Mockito.when;

public class ClienteFormControllerTest extends BaseUITest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AlertService alertService;

    @MockitoBean
    private EstadoService estadoService;

    private ClienteFormController controller;

    @Override
    public void start(Stage stage) throws Exception {
        when(estadoService.listarTodos()).thenReturn(criarEstadosMock());

        alertService.setTestMode(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cliente/cliente-form.fxml"));
        loader.setControllerFactory(applicationContext::getBean);

        Scene scene = new Scene(loader.load(), 1000, 750);
        stage.setScene(scene);
        stage.show();

        this.controller = loader.getController();
    }

    @Test
    void deveCarregarComponentesPrincipais() {
        assertNodeExists("#btnVoltar");

        assertNodeExists("#txtNome");
        assertNodeExists("#txtTelefone");
        assertNodeExists("#txtEmail");

        assertNodeExists("#txtCidade");
        assertNodeExists("#txtBairro");
        assertNodeExists("#txtCep");
        assertNodeExists("#txtLogradouro");
        assertNodeExists("#txtNumero");

        assertNodeExists("#comboEstado");
        assertNodeExists("#btnSalvar");
    }

    @Test
    void deveCarregarEstadosNoComboBox() {
        ComboBox<Estado> combo = findNode("#comboEstado", ComboBox.class);

        waitForFxEvents();

        assertThat(combo.getItems()).hasSize(4);
        assertThat(combo.getItems().get(0).getNome()).isEqualTo("Selecione");
        assertThat(combo.getItems().get(1).getNome()).isEqualTo("São Paulo");
        assertThat(combo.getItems().get(2).getNome()).isEqualTo("Rio de Janeiro");
        assertThat(combo.getItems().get(3).getNome()).isEqualTo("Minas Gerais");
    }

    @Test
    void deveExibirTituloCadastrarCliente() {
        var lblTitulo = findNode("#lblTitulo", javafx.scene.control.Label.class);
        assertThat(lblTitulo.getText()).isEqualTo("Cadastrar Cliente");
    }

    @Test
    void devePreencherCamposDoFormulario() {
        var txtNome = findNode("#txtNome", javafx.scene.control.TextField.class);
        var txtEmail = findNode("#txtEmail", javafx.scene.control.TextField.class);

        interact(() -> {
            txtNome.setText("João Silva");
            txtEmail.setText("joao@email.com");
        });

        assertThat(txtNome.getText()).isEqualTo("João Silva");
        assertThat(txtEmail.getText()).isEqualTo("joao@email.com");
    }

    @Test
    void deveSelecionarEstadoNoComboBox() {
        ComboBox<Estado> combo = findNode("#comboEstado", ComboBox.class);

        waitForFxEvents();

        interact(() -> combo.getSelectionModel().select(2));

        assertThat(combo.getSelectionModel().getSelectedItem().getNome())
                .isEqualTo("Rio de Janeiro");
    }

    @Test
    void deveExibirErroAoCadastrarSemNome() {
        clickOn("#btnSalvar");

        waitForFxEvents();

        Alert lastAlert = alertService.getLastAlert();
        assertThat(lastAlert).isNotNull();
        assertThat(lastAlert.getAlertType()).isEqualTo(Alert.AlertType.WARNING);
        assertThat(lastAlert.getTitle()).isEqualTo("Campos obrigatórios");
        assertThat(lastAlert.getContentText()).isEqualTo("O campo nome é obrigatório.");

        Platform.runLater(() -> lastAlert.close());
        waitForFxEvents();
    }

    @Test
    void deveExibirErroAoCadastrarSemTelefone() {
        var txtNome = findNode("#txtNome", javafx.scene.control.TextField.class);

        interact(() -> txtNome.setText("João Silva"));

        clickOn("#btnSalvar");
        waitForFxEvents();

        Alert lastAlert = alertService.getLastAlert();
        assertThat(lastAlert).isNotNull();
        assertThat(lastAlert.getAlertType()).isEqualTo(Alert.AlertType.WARNING);
        assertThat(lastAlert.getContentText()).isEqualTo("O campo telefone é obrigatório.");

        Platform.runLater(() -> lastAlert.close());
        waitForFxEvents();
    }

    @Test
    void deveExibirErroComTelefoneInvalido() {
        var txtNome = findNode("#txtNome", javafx.scene.control.TextField.class);
        var txtTelefone = findNode("#txtTelefone", javafx.scene.control.TextField.class);

        interact(() -> {
            txtNome.setText("João Silva");
            txtTelefone.setText("123"); // telefone inválido
        });

        clickOn("#btnSalvar");
        waitForFxEvents();

        Alert lastAlert = alertService.getLastAlert();
        assertThat(lastAlert.getContentText()).isEqualTo("O telefone é inválido.");

        Platform.runLater(() -> lastAlert.close());
        waitForFxEvents();
    }

    @Test
    void deveExibirErroComEmailInvalido() {
        var txtNome = findNode("#txtNome", javafx.scene.control.TextField.class);
        var txtTelefone = findNode("#txtTelefone", javafx.scene.control.TextField.class);
        var txtEmail = findNode("#txtEmail", javafx.scene.control.TextField.class);

        interact(() -> {
            txtNome.setText("João Silva");
            txtTelefone.setText("11987654321");
            txtEmail.setText("email-invalido");
        });

        clickOn("#btnSalvar");
        waitForFxEvents();

        Alert lastAlert = alertService.getLastAlert();
        assertThat(lastAlert.getContentText()).isEqualTo("O e-mail é inválido.");

        Platform.runLater(() -> lastAlert.close());
        waitForFxEvents();
    }

    @Test
    void deveExibirErroSemEstado() {
        var txtNome = findNode("#txtNome", javafx.scene.control.TextField.class);
        var txtTelefone = findNode("#txtTelefone", javafx.scene.control.TextField.class);
        var txtCidade = findNode("#txtCidade", javafx.scene.control.TextField.class);

        interact(() -> {
            txtNome.setText("João Silva");
            txtTelefone.setText("11987654321");
            txtCidade.setText("São Paulo");
        });

        clickOn("#btnSalvar");
        waitForFxEvents();

        Alert lastAlert = alertService.getLastAlert();
        assertThat(lastAlert.getContentText()).isEqualTo("O campo estado é obrigatório.");

        Platform.runLater(() -> lastAlert.close());
        waitForFxEvents();
    }



    private List<Estado> criarEstadosMock() {
        return Arrays.asList(
                criarEstado(1L, "São Paulo"),
                criarEstado(2L, "Rio de Janeiro"),
                criarEstado(3L, "Minas Gerais")
        );
    }

    private Estado criarEstado(Long id, String nome) {
        Estado estado = new Estado();
        estado.setId(id);
        estado.setNome(nome);
        return estado;
    }
}
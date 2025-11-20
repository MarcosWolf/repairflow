package com.marcoswolf.crm.reparos.ui.controller;

import com.marcoswolf.crm.reparos.ui.BaseUITest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class MainViewControllerTest extends BaseUITest {
    @Autowired
    private ApplicationContext applicationContext;

    private MainViewController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-view.fxml"));
        loader.setControllerFactory(applicationContext::getBean);

        Scene scene = new Scene(loader.load(), 1024, 768);
        scene.getStylesheets().add(getClass().getResource("/css/light-theme.css").toExternalForm());

        controller = loader.getController();

        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void deveCarregarMenuBarCorretamente() {
        verifyThat("Arquivo", hasText("Arquivo"));
        verifyThat("Clientes", hasText("Clientes"));
        verifyThat("Equipamentos", hasText("Equipamentos"));
        verifyThat("Reparos", hasText("Reparos"));
        verifyThat("Financeiro", hasText("Financeiro"));
        verifyThat("Ajuda", hasText("Ajuda"));
    }

    @Test
    public void deveCarregarContentArea() {
        AnchorPane contentArea = controller.getContentArea();
        assertThat(contentArea).isNotNull();
    }

    @Test
    public void deveAbrirMenuClientes() {
        clickOn("Clientes");

        verifyThat("Cadastrar Cliente", hasText("Cadastrar Cliente"));
        verifyThat("Gerenciar Clientes", hasText("Gerenciar Clientes"));
    }

    @Test
    public void deveCarregarFormularioClienteNoContentArea() {
        clickOn("Clientes");
        clickOn("Cadastrar Cliente");

        waitForFxEvents();

        AnchorPane contentArea = controller.getContentArea();
        assertThat(contentArea.getChildren()).isNotEmpty();

        TextField txtNome = lookup("#txtNome").queryAs(TextField.class);
        Button btnSalvar = lookup("#btnSalvar").queryAs(Button.class);

        assertThat(txtNome).isNotNull();
        assertThat(btnSalvar).isNotNull();
        assertThat(txtNome.getId()).isEqualTo("txtNome");
        assertThat(btnSalvar.getText()).isEqualTo("Salvar");
    }

    @Test
    public void deveCarregarGerenciarClientesNoContentArea() {
        clickOn("Clientes");
        clickOn("Gerenciar Clientes");

        waitForFxEvents();

        AnchorPane contentArea = controller.getContentArea();
        assertThat(contentArea.getChildren()).isNotEmpty();
    }
}
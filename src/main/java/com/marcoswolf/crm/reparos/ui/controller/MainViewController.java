package com.marcoswolf.crm.reparos.ui.controller;

import com.marcoswolf.crm.reparos.ui.config.SpringFXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MainViewController {
    private final SpringFXMLLoader fxmlLoader;
    private final ApplicationContext context;

    @FXML private VBox rootVbox;
    @FXML private AnchorPane contentArea;

    // Cliente
    @FXML private MenuItem menuCadastrarCliente;
    @FXML private MenuItem menuGerenciarCliente;

    @FXML
    public void initialize() {
        menuCadastrarCliente.setOnAction(e -> abrirTela("/fxml/cliente/cliente-form.fxml", null));
        menuGerenciarCliente.setOnAction(e -> abrirTela("/fxml/cliente/cliente-gerenciar.fxml", null));
    }

    public void abrirTela(String caminhoFXML, Object parametro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
            loader.setControllerFactory(clazz -> context.getBean(clazz));
            Pane novaTela = loader.load();

            Object controller = loader.getController();

            if (controller != null) {
                try {
                    if (parametro != null) {
                        controller.getClass()
                                .getMethod("setData", parametro.getClass())
                                .invoke(controller, parametro);
                    } else {
                        try
                        {
                            controller.getClass()
                                    .getMethod("setData", Object.class)
                                    .invoke(controller, new Object[]{null});
                        } catch (NoSuchMethodException ignored) {}
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            contentArea.getChildren().setAll(novaTela);
            AnchorPane.setTopAnchor(novaTela, 0.0);
            AnchorPane.setRightAnchor(novaTela, 0.0);
            AnchorPane.setBottomAnchor(novaTela, 0.0);
            AnchorPane.setLeftAnchor(novaTela, 0.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

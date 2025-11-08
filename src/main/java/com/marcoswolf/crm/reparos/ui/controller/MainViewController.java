package com.marcoswolf.crm.reparos.ui.controller;

import com.marcoswolf.crm.reparos.ui.config.SpringFXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

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

    // Equipamento
    @FXML private MenuItem menuCadastrarEquipamento;
    @FXML private MenuItem menuGerenciarEquipamento;
    @FXML private MenuItem menuCadastrarTipoEquipamento;
    @FXML private MenuItem menuGerenciarTipoEquipamento;

    @FXML
    public void initialize() {
        // Cliente
        menuCadastrarCliente.setOnAction(e -> abrirTela("/fxml/cliente/cliente-form.fxml", null));
        menuGerenciarCliente.setOnAction(e -> abrirTela("/fxml/cliente/cliente-gerenciar.fxml", null));

        // Equipamento
        menuCadastrarTipoEquipamento.setOnAction(e -> abrirTela("/fxml/tipoEquipamento/tipoEquipamento-form.fxml", null));
        menuGerenciarTipoEquipamento.setOnAction(e -> abrirTela("/fxml/tipoEquipamento/tipoEquipamento-gerenciar.fxml", null));
    }

    public void abrirTela(String caminhoFXML, Object parametro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
            loader.setControllerFactory(clazz -> context.getBean(clazz));
            Pane novaTela = loader.load();

            Object controller = loader.getController();
            chamarSetData(controller, parametro);

            contentArea.getChildren().setAll(novaTela);
            AnchorPane.setTopAnchor(novaTela, 0.0);
            AnchorPane.setRightAnchor(novaTela, 0.0);
            AnchorPane.setBottomAnchor(novaTela, 0.0);
            AnchorPane.setLeftAnchor(novaTela, 0.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chamarSetData(Object controller, Object parametro) {
        if (controller == null || parametro == null) return;

        try {
            controller.getClass()
                    .getMethod("setData", parametro.getClass())
                    .invoke(controller, parametro);
        } catch (NoSuchMethodException e1) {
            try {
                controller.getClass()
                        .getMethod("setData", Object.class)
                        .invoke(controller, parametro);
            } catch (NoSuchMethodException ignored) {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

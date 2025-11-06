package com.marcoswolf.crm.reparos.ui.controller;

import com.marcoswolf.crm.reparos.ui.config.SpringFXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MainViewController {
    private final SpringFXMLLoader fxmlLoader;

    @FXML private VBox rootVbox;
    @FXML private AnchorPane contentArea;

    // Cliente
    @FXML private MenuItem menuCadastrarCliente;
    @FXML private MenuItem menuGerenciarCliente;

    @FXML
    public void initialize() {
        menuCadastrarCliente.setOnAction(e -> abrirTela("/fxml/cliente/cliente-form.fxml"));
        menuGerenciarCliente.setOnAction(e -> abrirTela("/fxml/cliente/cliente-gerenciar.fxml"));
    }

    private void abrirTela(String caminhoFXML) {
        try {
            Pane novaTela = fxmlLoader.load(getClass().getResource(caminhoFXML));
            estruturarTela(novaTela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void estruturarTela(Pane novaTela) {
        contentArea.getChildren().setAll(novaTela);
        AnchorPane.setTopAnchor(novaTela, 0.0);
        AnchorPane.setRightAnchor(novaTela, 0.0);
        AnchorPane.setBottomAnchor(novaTela, 0.0);
        AnchorPane.setLeftAnchor(novaTela, 0.0);
    }
}

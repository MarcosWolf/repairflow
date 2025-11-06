package com.marcoswolf.crm.reparos.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainViewController {
    @FXML
    private VBox rootVbox;

    @FXML
    private AnchorPane contentArea;

    @FXML
    private MenuItem menuCadastrarCliente;

    @FXML
    public void initialize() {
        menuCadastrarCliente.setOnAction(e -> abrirCadastroCliente());
    }

    private void abrirCadastroCliente() {
        try
        {
            Pane telaCliente = FXMLLoader.load(getClass().getResource("/fxml/cliente/cliente-form.fxml"));
            contentArea.getChildren().setAll(telaCliente);

            AnchorPane.setTopAnchor(telaCliente, 0.0);
            AnchorPane.setRightAnchor(telaCliente, 0.0);
            AnchorPane.setBottomAnchor(telaCliente, 0.0);
            AnchorPane.setLeftAnchor(telaCliente, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

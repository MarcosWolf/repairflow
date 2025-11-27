package com.marcoswolf.repairflow.ui.controller;

import com.marcoswolf.repairflow.ui.navigation.ViewNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainViewController {
    private final ViewNavigator navigator;

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

    // Reparo
    @FXML private MenuItem menuCadastrarReparo;
    @FXML private MenuItem menuGerenciarReparo;
    @FXML private MenuItem menuCadastrarStatusReparo;
    @FXML private MenuItem menuGerenciarStatusReparo;

    @FXML
    public void initialize() {
        menuCadastrarCliente.setOnAction(e -> open("/fxml/cliente/cliente-form.fxml"));
        menuGerenciarCliente.setOnAction(e -> open("/fxml/cliente/cliente-gerenciar.fxml"));

        menuCadastrarEquipamento.setOnAction(e -> open("/fxml/equipamento/equipamento-form.fxml"));
        menuGerenciarEquipamento.setOnAction(e -> open("/fxml/equipamento/equipamento-gerenciar.fxml"));
        menuCadastrarTipoEquipamento.setOnAction(e -> open("/fxml/tipoEquipamento/tipoEquipamento-form.fxml"));
        menuGerenciarTipoEquipamento.setOnAction(e -> open("/fxml/tipoEquipamento/tipoEquipamento-gerenciar.fxml"));

        menuCadastrarReparo.setOnAction(e -> open("/fxml/reparo/reparo-form.fxml"));
        menuGerenciarReparo.setOnAction(e -> open("/fxml/reparo/reparo-gerenciar.fxml"));
        menuCadastrarStatusReparo.setOnAction(e -> open("/fxml/statusReparo/statusReparo-form.fxml"));
        menuGerenciarStatusReparo.setOnAction(e -> open("/fxml/statusReparo/statusReparo-gerenciar.fxml"));
    }

    private void open(String fxmlPath) {
        navigator.openView(fxmlPath, contentArea, null);
    }

    public AnchorPane getContentArea() {
        return contentArea;
    }
}
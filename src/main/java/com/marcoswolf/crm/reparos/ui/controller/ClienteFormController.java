package com.marcoswolf.crm.reparos.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ClienteFormController {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private void onCancelar() {
        ((AnchorPane) rootPane.getParent()).getChildren().remove(rootPane);
    }

}

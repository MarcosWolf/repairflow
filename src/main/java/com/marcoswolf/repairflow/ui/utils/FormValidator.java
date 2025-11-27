package com.marcoswolf.repairflow.ui.utils;

import com.marcoswolf.repairflow.infrastructure.entities.Estado;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Map;

public class FormValidator {
    public static boolean validarCamposObrigatorios(Map<String, Object> campos) {
        StringBuilder erros = new StringBuilder();

        for (Map.Entry<String, Object> entry : campos.entrySet()) {
            String nomeCampo = entry.getKey();
            Object campo = entry.getValue();

            if (campo instanceof TextField textField) {
                if (textField.getText() == null || textField.getText().trim().isEmpty()) {
                    erros.append("- ").append(nomeCampo).append(" é obrigatório.\n");
                }
            } else if (campo instanceof ComboBox<?> comboBox) {
                Object valor = comboBox.getValue();
                if (valor == null || (valor instanceof Estado e && e.getId() == 0)) {
                    erros.append("- ").append(nomeCampo).append(" é obrigatório.\n");
                }
            }
        }

        if (erros.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatórios");
            alert.setHeaderText("Por favor, preencha os campos obrigatórios:");
            alert.setContentText(erros.toString());
            alert.showAndWait();
            return false;
        }

        return true;
    }
}

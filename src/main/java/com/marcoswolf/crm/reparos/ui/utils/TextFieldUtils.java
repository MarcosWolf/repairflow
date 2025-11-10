package com.marcoswolf.crm.reparos.ui.utils;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class TextFieldUtils {
    public static void aplicarLimite(TextField campo, int limite) {
        campo.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().length() <= limite ? change : null
        ));
    }

    public static void apenasNumeros(TextField campo, int limite) {
        campo.setTextFormatter(new TextFormatter<>(change -> {
            String novoTexto = change.getControlNewText();
            if (novoTexto.matches("\\d{0," + limite + "}")) {
                return change;
            }
            return null;
        }));
    }

    public static void apenasLetras(TextField campo, int limite) {
        campo.setTextFormatter(new TextFormatter<>(change -> {
            String novoTexto = change.getControlNewText();
            if (novoTexto.matches("[a-zA-ZÀ-ÿ ]{0," + limite + "}")) {
                return change;
            }
            return null;
        }));
    }

    public static void aplicarMascaraNumerica(TextField textField) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null || newValue.isBlank()) return;

            String valor = newValue.replace(",", ".");

            if (!valor.matches("[0-9]*\\.?[0-9]*")) {
                valor = valor.replaceAll("[^0-9.]", "");
            }

            if (valor.chars().filter(ch -> ch == '.').count() > 1) {
                valor = oldValue != null ? oldValue : "";
            }

            if (valor.contains(".")) {
                int index = valor.indexOf(".");
                if (valor.length() > index + 3) {
                    valor = valor.substring(0, index + 3);
                }
            }

            if (!valor.equals(newValue)) {
                textField.setText(valor);
            }
        });
    }
}

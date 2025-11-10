package com.marcoswolf.crm.reparos.ui.utils;

import javafx.scene.control.TextField;

public class ParseUtils {
    public static Integer parseInteger(TextField campo) {
        if (campo == null || campo.getText() == null) {
            return null;
        }

        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            return null;
        }

        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double parseDouble(TextField field) {
        if (field == null || field.getText() == null || field.getText().isBlank()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(field.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private static String limparTexto(TextField campo) {
        if (campo == null || campo.getText() == null) return "";
        return campo.getText().trim();
    }
}

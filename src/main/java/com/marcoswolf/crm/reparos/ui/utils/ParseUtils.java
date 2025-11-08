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

    public static Double parseDouble(TextField campo) {
        if (campo == null || campo.getText() == null) {
            return null;
        }

        String texto = campo.getText().trim().replace(",", ".");
        if (texto.isEmpty()) {
            return null;
        }

        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

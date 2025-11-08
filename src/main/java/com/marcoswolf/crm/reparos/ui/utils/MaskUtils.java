package com.marcoswolf.crm.reparos.ui.utils;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class MaskUtils {
    public static void aplicarMascaraTelefone(TextField campo) {
        campo.setTextFormatter(new TextFormatter<>(change -> {
            String texto = change.getControlNewText().replaceAll("[^0-9]", "");

            if (texto.length() > 11) {
                texto = texto.substring(0, 11);
            }

            StringBuilder formatado = new StringBuilder();
            int len = texto.length();

            if (len > 0) formatado.append("(");
            if (len >= 1) formatado.append(texto, 0, Math.min(2, len));
            if (len >= 3) formatado.append(") ");
            if (len >= 3) formatado.append(texto, 2, Math.min(7, len));
            if (len >= 8) formatado.append("-").append(texto.substring(7));

            change.setText(formatado.toString());
            change.setRange(0, change.getControlText().length());
            change.setCaretPosition(formatado.length());
            change.setAnchor(formatado.length());
            return change;
        }));
    }

    public static void aplicarMascaraCEP(TextField campo) {
        campo.textProperty().addListener((obs, oldText, newText) -> {
            String digits = newText.replaceAll("\\D", "");
            if (digits.length() > 8) digits = digits.substring(0, 8);

            StringBuilder formatted = new StringBuilder();
            if (digits.length() > 5) {
                formatted.append(digits, 0, 5)
                        .append("-")
                        .append(digits.substring(5));
            } else {
                formatted.append(digits);
            }

            if (!formatted.toString().equals(newText)) {
                campo.setText(formatted.toString());
                campo.positionCaret(formatted.length());
            }
        });
    }
}

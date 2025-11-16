package com.marcoswolf.crm.reparos.ui.utils;

import javafx.application.Platform;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.function.UnaryOperator;

public class MaskUtils {
    public static void aplicarMascaraData(DatePicker datePicker) {
        TextField editor = datePicker.getEditor();
        editor.textProperty().addListener((obs, old, neu) -> {
            if (neu == null) return;

            String digits = neu.replaceAll("[^0-9]", "");

            if (digits.length() > 8) {
                digits = digits.substring(0, 8);
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < digits.length(); i++) {
                sb.append(digits.charAt(i));
                if ((i == 1 || i == 3) && i != digits.length() - 1) {
                    sb.append('/');
                }
            }

            if (!sb.toString().equals(neu)) {
                editor.setText(sb.toString());
            }
        });
    }

    public static void aplicarMascaraMonetaria(TextField textField) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (newText.isEmpty()) {
                return change;
            }

            String digitsOnly = newText.replaceAll("[^\\d]", "");

            if (digitsOnly.length() > 7) {
                return null;
            }

            if (!digitsOnly.isEmpty()) {
                try {
                    long valorEmCentavos = Long.parseLong(digitsOnly);
                    double valor = valorEmCentavos / 100.0;

                    String valorFormatado = df.format(valor);

                    change.setText(valorFormatado);
                    change.setRange(0, change.getControlText().length());
                    change.setCaretPosition(valorFormatado.length());
                    change.setAnchor(valorFormatado.length());

                    return change;
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            return change;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textField.setTextFormatter(textFormatter);
    }

    public static void aplicarMascaraTelefone(TextField campo) {
        campo.setTextFormatter(new TextFormatter<>(change -> {
            String texto = change.getControlNewText().replaceAll("[^0-9]", "");

            if (texto.length() > 11) {
                texto = texto.substring(0, 11);
            }

            StringBuilder formatado = new StringBuilder();
            int len = texto.length();

            if (len > 0) {
                formatado.append("(");
                formatado.append(texto.substring(0, Math.min(2, len)));
            }

            if (len >= 3) {
                formatado.append(") ");

                if (len == 11) {
                    formatado.append(texto, 2, 7);
                    if (len > 7) {
                        formatado.append("-").append(texto.substring(7));
                    }
                }
                else {
                    formatado.append(texto, 2, Math.min(6, len));
                    if (len > 6) {
                        formatado.append("-").append(texto.substring(6));
                    }
                }
            }

            change.setText(formatado.toString());
            change.setRange(0, change.getControlText().length());
            change.setCaretPosition(formatado.length());
            change.setAnchor(formatado.length());
            return change;
        }));
    }

    public static void aplicarMascaraCEP(TextField campo) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            String digits = newText.replaceAll("\\D", "");

            if (digits.length() > 8) {
                return null;
            }

            StringBuilder formatted = new StringBuilder();
            if (digits.length() > 5) {
                formatted.append(digits, 0, 5)
                        .append("-")
                        .append(digits.substring(5));
            } else {
                formatted.append(digits);
            }

            String formattedText = formatted.toString();

            if (!formattedText.equals(newText)) {
                change.setText(formattedText);
                change.setRange(0, change.getControlText().length());
                change.setCaretPosition(formattedText.length());
                change.setAnchor(formattedText.length());
            }

            return change;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        campo.setTextFormatter(textFormatter);
    }
}

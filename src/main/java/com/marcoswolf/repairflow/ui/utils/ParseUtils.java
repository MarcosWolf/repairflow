package com.marcoswolf.repairflow.ui.utils;

import javafx.scene.control.TextField;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

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

    public static BigDecimal parseBigDecimal(TextField field) {
        if (field == null || field.getText() == null || field.getText().isBlank()) {
            return BigDecimal.ZERO;
        }
        try {
            String valor = field.getText().replace(",", ".").trim();
            return new BigDecimal(valor);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal parseValorBR(String txt) {
        if (txt == null || txt.isBlank()) return BigDecimal.ZERO;
        txt = txt.replace(".", "").replace(",", ".");
        return new BigDecimal(txt);
    }

    public static String formatarValorBR(BigDecimal valor) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(valor);
    }
}

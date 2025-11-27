package com.marcoswolf.repairflow.ui.utils;

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


}

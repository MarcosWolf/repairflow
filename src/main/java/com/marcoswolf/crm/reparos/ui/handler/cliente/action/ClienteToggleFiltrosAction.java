package com.marcoswolf.crm.reparos.ui.handler.cliente;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

@Component
public class ClienteToggleFiltrosAction {
    public boolean executar(boolean filtrosVisiveis, VBox filtroPane) {
        if (!filtrosVisiveis) {
            mostrarFiltros(filtroPane);
        } else {
            ocultarFiltros(filtroPane);
        }

        return !filtrosVisiveis;
    }

    private void mostrarFiltros(VBox filtroPane) {
        filtroPane.setVisible(true);
        filtroPane.setManaged(true);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), filtroPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        TranslateTransition slideDown = new TranslateTransition(Duration.millis(200), filtroPane);
        slideDown.setFromY(-10);
        slideDown.setToY(0);

        fadeIn.play();
        slideDown.play();
    }

    private void ocultarFiltros(VBox filtroPane) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), filtroPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        TranslateTransition slideUp = new TranslateTransition(Duration.millis(200), filtroPane);
        slideUp.setFromY(0);
        slideUp.setToY(-10);

        fadeOut.setOnFinished(e -> {
            filtroPane.setVisible(false);
            filtroPane.setManaged(false);
        });

        fadeOut.play();
        slideUp.play();
    }
}

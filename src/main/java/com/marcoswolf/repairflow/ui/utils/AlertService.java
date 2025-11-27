package com.marcoswolf.repairflow.ui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AlertService {
    private boolean testMode = false;
    private Alert lastAlert = null;
    private ButtonType mockConfirmResponse = ButtonType.OK;

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public Alert getLastAlert() {
        return lastAlert;
    }

    public void setMockConfirmResponse(ButtonType response) {
        this.mockConfirmResponse = response;
    }

    public void setMockConfirmResponse(boolean confirmed) {
        this.mockConfirmResponse = confirmed ? ButtonType.OK : ButtonType.CANCEL;
    }

    public void info(String title, String message) {
        show(Alert.AlertType.INFORMATION, title, message);
    }

    public void warn(String title, String message) {
        show(Alert.AlertType.WARNING, title, message);
    }

    public void error(String title, String message) {
        show(Alert.AlertType.ERROR, title, message);
    }

    public boolean confirm(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText("Esta ação não poderá ser desfeita.");

        lastAlert = alert;

        if (testMode) {
            alert.show();
            return mockConfirmResponse == ButtonType.OK;
        } else {
            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        }
    }

    private void show(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        lastAlert = alert;

        if (testMode) {
            alert.show();
        } else {
            alert.showAndWait();
        }
    }
}
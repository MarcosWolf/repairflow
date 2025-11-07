package com.marcoswolf.crm.reparos.ui.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class SpringFXMLLoader {

    private final ApplicationContext context;
    private FXMLLoader loader;

    public SpringFXMLLoader(ApplicationContext context) {
        this.context = context;
    }

    public Pane load(URL url) {
        try {
            loader = new FXMLLoader(url);
            loader.setControllerFactory(context::getBean);
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar FXML: " + url, e);
        }
    }

    public Object getController() {
        return loader != null ? loader.getController() : null;
    }
}
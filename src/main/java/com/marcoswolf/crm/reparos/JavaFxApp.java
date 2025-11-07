package com.marcoswolf.crm.reparos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URL;

public class JavaFxApp extends Application {
    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = new SpringApplicationBuilder(CrmReparosApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmLoader = new FXMLLoader(getClass().getResource("/fxml/main-view.fxml"));
        fxmLoader.setControllerFactory(springContext::getBean);

        Scene scene = new Scene(fxmLoader.load(), 860, 600);

        URL cssUrl = getClass().getResource("/css/light-theme.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS não encontrado");
        }
        stage.setScene(scene);
        stage.setTitle("crm-reparos");
        stage.show();
    }

    @Override
    public void stop() {
        springContext.close();
    }
}

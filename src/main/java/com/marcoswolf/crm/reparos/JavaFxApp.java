package com.marcoswolf.crm.reparos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

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

        Scene scene = new Scene(fxmLoader.load(), 1200, 800);
        stage.setScene(scene);
        stage.setTitle("crm-reparos");
        stage.show();
    }

    @Override
    public void stop() {
        springContext.close();
    }
}

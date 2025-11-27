package com.marcoswolf.repairflow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URL;

public class JavaFxApp extends Application {
    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = new SpringApplicationBuilder(RepairFlowApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmLoader = new FXMLLoader(getClass().getResource("/fxml/main-view.fxml"));
        fxmLoader.setControllerFactory(springContext::getBean);

        Scene scene = new Scene(fxmLoader.load());

        URL cssUrl = getClass().getResource("/css/light-theme.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS não encontrado");
        }
        stage.setScene(scene);
        stage.setTitle("crm-reparos");

        stage.setMinWidth(1024);
        stage.setMinHeight(800);
        stage.setWidth(1360);
        stage.setHeight(800);

        stage.show();
    }

    @Override
    public void stop() {
        springContext.close();
    }
}

package com.marcoswolf.repairflow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
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
        String version = "v0.8";
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
        stage.setTitle("RepairFlow " + version);

        double defaultWidth = 1360;
        double defaultHeight = 800;

        stage.setMinWidth(1024);
        stage.setMinHeight(800);
        stage.setWidth(defaultWidth);
        stage.setHeight(defaultHeight);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        boolean shouldMaximize =
                defaultWidth >= screenWidth * 0.9 ||
                        defaultHeight >= screenHeight * 0.9;

        if (shouldMaximize) {
            stage.setMaximized(true);
        }

        stage.show();
    }

    @Override
    public void stop() {
        springContext.close();
    }
}

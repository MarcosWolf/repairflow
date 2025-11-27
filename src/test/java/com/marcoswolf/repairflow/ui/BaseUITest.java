package com.marcoswolf.repairflow.ui;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({ApplicationExtension.class, SpringExtension.class})
@SpringBootTest
public abstract class BaseUITest extends ApplicationTest {

    @BeforeAll
    public static void setupSpec() throws Exception {

    }

    @Override
    public void start(Stage stage) throws Exception {
    }

    @AfterEach
    public void afterEachTest() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @AfterAll
    public static void cleanupSpec() throws TimeoutException {
        FxToolkit.cleanupApplication(new javafx.application.Application() {
            @Override
            public void start(Stage primaryStage) {
            }
        });
    }

    protected void waitForFxEvents() {
        org.testfx.util.WaitForAsyncUtils.waitForFxEvents();
    }

    protected void assertNodeExists(String query) {
        Node node = lookup(query).query();
        assertThat(node).as("Node com query '%s' deve existir", query).isNotNull();
    }

    protected void assertNodeNotExists(String query) {
        Node node = lookup(query).query();
        assertThat(node).as("Node com query '%s' não deve existir", query).isNull();
    }

    protected void assertAnchorPaneHasChildren(AnchorPane anchorPane) {
        assertThat(anchorPane).isNotNull();
        assertThat(anchorPane.getChildren()).isNotEmpty();
    }

    protected void assertAnchorPaneIsEmpty(AnchorPane anchorPane) {
        assertThat(anchorPane).isNotNull();
        assertThat(anchorPane.getChildren()).isEmpty();
    }

    protected <T extends Node> T findNode(String query, Class<T> type) {
        return lookup(query).queryAs(type);
    }
}
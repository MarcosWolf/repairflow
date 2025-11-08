package com.marcoswolf.crm.reparos.ui.navigation;

import com.marcoswolf.crm.reparos.ui.config.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ViewNavigator {
    private final ApplicationContext context;
    private final SpringFXMLLoader fxmlLoader;

    public <T> void openView(String fxmlPath, AnchorPane targetArea, T data) {
        try {
            targetArea.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(context::getBean);
            Pane newView = loader.load();

            Object controller = loader.getController();
            if (controller instanceof com.marcoswolf.crm.reparos.ui.interfaces.DataReceiver<?> receiver) {
                ((com.marcoswolf.crm.reparos.ui.interfaces.DataReceiver<T>) receiver).setData(data);
            }

            setAnchors(targetArea, newView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void openViewRootPane(String fxmlPath, AnchorPane currentRoot, T data) {
        try {
            var parent = currentRoot.getParent();
            if (parent instanceof AnchorPane anchorPane) {
                openView(fxmlPath, anchorPane, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAnchors(AnchorPane contentArea, Pane view) {
        contentArea.getChildren().setAll(view);
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
    }

    public void closeCurrentView(AnchorPane view) {
        var parent = view.getParent();
        if (parent instanceof AnchorPane anchorPane) {
            anchorPane.getChildren().remove(view);
        }
    }
}

package com.marcoswolf.repairflow.ui.utils;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class TableButtonFactory {
    public static <T> Callback<TableColumn<T, Void>, TableCell<T, Void>> actionButton(
            String label,
            String style,
            java.util.function.Consumer<T> action
    ) {
        return col -> new TableCell<>() {
            private final Button btn = new Button(label);

            {
                if (style != null) {
                    btn.setStyle(style);
                }
                btn.setOnAction(e -> {
                    T item = getTableView().getItems().get(getIndex());
                    action.accept(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        };
    }
}

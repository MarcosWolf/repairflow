package com.marcoswolf.repairflow.ui.tables;

import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import com.marcoswolf.repairflow.ui.utils.TableUtils;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Optional;
import java.util.function.Consumer;

public class StatusReparoTableView {
    public static void configurarTabela(
        TableView<StatusReparo> tabela,
        TableColumn<StatusReparo, String> colNome,
        TableColumn<StatusReparo, Number> colTotalReparos,
        Consumer<StatusReparo> duploCliqueAcao
    ) {
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        colTotalReparos.setCellValueFactory(c -> new SimpleLongProperty(
                Optional.ofNullable(c.getValue().getTotalReparos()).orElse(0L)
        ));

        TableUtils.setDoubleClickAction(tabela, duploCliqueAcao);
        TableUtils.centralizarColuna(colTotalReparos);
    }
}
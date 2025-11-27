package com.marcoswolf.repairflow.ui.tables;

import com.marcoswolf.repairflow.infrastructure.entities.Reparo;
import com.marcoswolf.repairflow.ui.utils.TableUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.function.Consumer;

public class ReparoTableView {
    public static void configurarTabela(
            TableView<Reparo> tabela,
            TableColumn<Reparo, String> colNome,
            Consumer<Reparo> duploCliqueAcao
    ) {
        colNome.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getEquipamento() != null
                        ? c.getValue().getEquipamento().getNome()
                        : ""
        ));

        TableUtils.setDoubleClickAction(tabela, duploCliqueAcao);
        //TableUtils.centralizarColuna(col);
    }
}

package com.marcoswolf.repairflow.ui.tables;

import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.ui.utils.TableUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.function.Consumer;

public class EquipamentoTableView {
    public static void configurarTabela(
            TableView<Equipamento> tabela,
            TableColumn<Equipamento, String> colMarca,
            TableColumn<Equipamento, String> colModelo,
            Consumer<Equipamento> duploCliqueAcao
    ) {
        colMarca.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMarca()));
        colModelo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getModelo()));

        TableUtils.setDoubleClickAction(tabela, duploCliqueAcao);
        TableUtils.centralizarColuna(colModelo);
    }
}

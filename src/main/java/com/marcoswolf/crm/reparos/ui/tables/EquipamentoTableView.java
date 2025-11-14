package com.marcoswolf.crm.reparos.ui.tables;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.ui.utils.TableUtils;
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

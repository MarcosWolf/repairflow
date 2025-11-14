package com.marcoswolf.crm.reparos.ui.tables;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.ui.utils.TableUtils;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Optional;
import java.util.function.Consumer;

public class TipoEquipamentoTableView {
    public static void configurarTabela(
            TableView<TipoEquipamento> tabela,
            TableColumn<TipoEquipamento, String> colNome,
            TableColumn<TipoEquipamento, Number> colTotalClientes,
            TableColumn<TipoEquipamento, Number> colTotalEquipamentos,
            TableColumn<TipoEquipamento, Number> colTotalReparos,
            Consumer<TipoEquipamento> duploCliqueAcao
    ) {
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        colTotalClientes.setCellValueFactory(c -> new SimpleLongProperty(
                Optional.ofNullable(c.getValue().getTotalClientes()).orElse(0L)
        ));
        colTotalEquipamentos.setCellValueFactory(c -> new SimpleLongProperty(
                Optional.ofNullable(c.getValue().getTotalEquipamentos()).orElse(0L)
        ));
        colTotalReparos.setCellValueFactory(c -> new SimpleLongProperty(
                Optional.ofNullable(c.getValue().getTotalReparos()).orElse(0L)
        ));

        TableUtils.setDoubleClickAction(tabela, duploCliqueAcao);
        TableUtils.centralizarColuna(colTotalClientes);
        TableUtils.centralizarColuna(colTotalEquipamentos);
        TableUtils.centralizarColuna(colTotalReparos);
    }
}

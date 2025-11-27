package com.marcoswolf.repairflow.ui.tables;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.ui.utils.TableUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.function.Consumer;

public class ClienteTableView {
    public static void configurarTabela(
            TableView<Cliente> tabela,
            TableColumn<Cliente, String> colNome,
            TableColumn<Cliente, String> colTelefone,
            TableColumn<Cliente, String> colCidade,
            TableColumn<Cliente, String> colEstado,
            Consumer<Cliente> duploCliqueAcao
    ) {
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        colTelefone.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefone()));
        colCidade.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getEndereco() != null ? c.getValue().getEndereco().getCidade() : ""
        ));
        colEstado.setCellValueFactory(c -> {
            var endereco = c.getValue().getEndereco();
            return new SimpleStringProperty(
                    endereco != null && endereco.getEstado() != null ? endereco.getEstado().getNome() : ""
            );
        });

        TableUtils.setDoubleClickAction(tabela, duploCliqueAcao);
        TableUtils.centralizarColuna(colCidade);
        TableUtils.centralizarColuna(colEstado);
        TableUtils.centralizarColuna(colTelefone);
    }
}

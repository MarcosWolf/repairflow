package com.marcoswolf.crm.reparos.ui.tables;

import com.marcoswolf.crm.reparos.infrastructure.entities.PecaPagamento;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.function.Consumer;

public class PecaPagamentoTableView {
    public static void configurarTabela(
            TableView<PecaPagamento> tabela,
            TableColumn<PecaPagamento, String> colDescricao,
            TableColumn<PecaPagamento, Integer> colQuantidade,
            TableColumn<PecaPagamento, String> colValorUnitario,
            TableColumn<PecaPagamento, String> colValorTotal,
            TableColumn<PecaPagamento, Void> colRemover,
            ObservableList<PecaPagamento> lista,
            Consumer<PecaPagamento> removerAcao
    ) {
        colDescricao.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        colQuantidade.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getQuantidade()));
        colValorUnitario.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getValorFormatado()));
        colValorTotal.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTotalLinhaFormatado()));
        colRemover.setCellFactory(criarBotaoRemover(lista, removerAcao));
        tabela.setItems(lista);
    }

    public static Callback<TableColumn<PecaPagamento, Void>, TableCell<PecaPagamento, Void>> criarBotaoRemover(
        ObservableList<PecaPagamento> lista,
        Consumer<PecaPagamento> removerAcao
    ) {
        return col -> new TableCell<>() {
            private final Button btn = new Button("X");

            {
                btn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
                btn.setOnAction(e -> {
                    PecaPagamento p = getTableView().getItems().get(getIndex());
                    lista.remove(p);
                    if (removerAcao != null) removerAcao.accept(p);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(btn));
            }
        };
    }
}

package com.marcoswolf.repairflow.ui.tables;

import com.marcoswolf.repairflow.infrastructure.entities.PecaPagamento;
import com.marcoswolf.repairflow.ui.utils.TableUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

        TableUtils.centralizarColuna(colQuantidade);
        TableUtils.centralizarColuna(colValorUnitario);
        TableUtils.centralizarColuna(colValorTotal);

        colRemover.setCellFactory(criarBotaoRemover(lista, removerAcao));

        tabela.setItems(lista);
    }

    public static Callback<TableColumn<PecaPagamento, Void>, TableCell<PecaPagamento, Void>> criarBotaoRemover(
            ObservableList<PecaPagamento> lista,
            Consumer<PecaPagamento> removerAcao
    ) {
        return col -> new TableCell<>() {
            private final Button btn = new Button("✕");

            {
                btn.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-text-fill: #e74c3c; " +
                                "-fx-font-size: 16px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-cursor: hand; " +
                                "-fx-padding: 5;"
                );

                // Hover effect
                btn.setOnMouseEntered(e -> btn.setStyle(
                        "-fx-background-color: #ffe6e6; " +
                                "-fx-text-fill: #c0392b; " +
                                "-fx-font-size: 16px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-cursor: hand; " +
                                "-fx-padding: 5; " +
                                "-fx-background-radius: 3;"
                ));

                btn.setOnMouseExited(e -> btn.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-text-fill: #e74c3c; " +
                                "-fx-font-size: 16px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-cursor: hand; " +
                                "-fx-padding: 5;"
                ));

                btn.setOnAction(e -> {
                    PecaPagamento p = getTableView().getItems().get(getIndex());
                    lista.remove(p);
                    if (removerAcao != null) removerAcao.accept(p);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                    setAlignment(Pos.CENTER);
                }
            }
        };
    }
}
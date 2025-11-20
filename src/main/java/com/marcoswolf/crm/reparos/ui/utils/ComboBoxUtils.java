package com.marcoswolf.crm.reparos.ui.utils;

import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ComboBoxUtils {

    /**
     * Método genérico para carregar qualquer ComboBox com um item "Selecione" no topo,
     * evitando duplicação.
     *
     * @param combo       O ComboBox a ser carregado
     * @param itens       Lista de itens vinda do serviço
     * @param getNomeFunc Função que retorna o nome/descrição do item
     * @param criarVazio  Função que cria um item vazio ("Selecione")
     * @param <T>         Tipo da entidade
     */
    public static <T> void carregarCombo(
            ComboBox<T> combo,
            List<T> itens,
            Function<T, String> getNomeFunc,
            Supplier<T> criarVazio
    ) {
        if (itens == null) {
            itens = new ArrayList<>();
        } else {
            itens = new ArrayList<>(itens);
        }

        T selecione = criarVazio.get();
        itens.add(0, selecione);

        combo.getItems().setAll(itens);

        combo.getSelectionModel().selectFirst();

        combo.setConverter(new StringConverter<>() {
            @Override
            public String toString(T item) {
                return item == null ? "" : getNomeFunc.apply(item);
            }

            @Override
            public T fromString(String string) {
                return combo.getItems().stream()
                        .filter(e -> getNomeFunc.apply(e).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }
}
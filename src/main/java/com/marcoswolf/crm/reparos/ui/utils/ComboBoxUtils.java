package com.marcoswolf.crm.reparos.ui.utils;

import com.marcoswolf.crm.reparos.business.estado.EstadoService;
import com.marcoswolf.crm.reparos.business.estado.IEstadoConsultaService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class ComboBoxUtils {
    public static void carregarEstados(ComboBox<Estado> comboEstado,
                                       IEstadoConsultaService estadoConsultaService) {

        List<Estado> estados = estadoConsultaService.listarTodos();
        if (estados == null) {
            estados = new ArrayList<>();
        }

        Estado selecione = new Estado();
        selecione.setId(0L);
        selecione.setNome("Selecione");

        estados.add(0, selecione);

        comboEstado.getItems().setAll(estados);
        comboEstado.getSelectionModel().selectFirst();

        comboEstado.setConverter(new StringConverter<>() {
            @Override
            public String toString(Estado estado) {
                return estado == null ? "" : estado.getNome();
            }

            @Override
            public Estado fromString(String string) {
                return comboEstado.getItems().stream()
                        .filter(e -> e.getNome().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }
}

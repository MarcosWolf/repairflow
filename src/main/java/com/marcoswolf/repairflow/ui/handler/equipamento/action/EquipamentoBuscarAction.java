package com.marcoswolf.repairflow.ui.handler.equipamento.action;

import com.marcoswolf.repairflow.business.equipamento.filtro.EquipamentoFiltro;
import com.marcoswolf.repairflow.business.equipamento.filtro.EquipamentoFiltroService;
import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EquipamentoBuscarAction {
    private final EquipamentoFiltroService equipamentoFiltroService;

    public List<Equipamento> executar(String nome) {
        try {
            var filtro = new EquipamentoFiltro();
            filtro.setNome(nome);
            return equipamentoFiltroService.aplicarFiltros(filtro);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}

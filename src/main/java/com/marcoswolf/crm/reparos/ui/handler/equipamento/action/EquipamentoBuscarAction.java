package com.marcoswolf.crm.reparos.ui.handler.equipamento.action;

import com.marcoswolf.crm.reparos.business.equipamento.filtro.EquipamentoFiltro;
import com.marcoswolf.crm.reparos.business.equipamento.filtro.EquipamentoFiltroService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
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

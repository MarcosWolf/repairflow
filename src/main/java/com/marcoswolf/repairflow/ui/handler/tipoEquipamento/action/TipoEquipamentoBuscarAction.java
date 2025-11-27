package com.marcoswolf.repairflow.ui.handler.tipoEquipamento.action;

import com.marcoswolf.repairflow.business.tipoEquipamento.filtro.TipoEquipamentoFiltro;
import com.marcoswolf.repairflow.business.tipoEquipamento.filtro.TipoEquipamentoFiltroService;
import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TipoEquipamentoBuscarAction {
    private final TipoEquipamentoFiltroService tipoEquipamentoFiltroService;

    public List<TipoEquipamento> executar(String nome) {
        try {
            var filtro = new TipoEquipamentoFiltro();
            filtro.setNome(nome);
            return tipoEquipamentoFiltroService.aplicarFiltros(filtro);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}

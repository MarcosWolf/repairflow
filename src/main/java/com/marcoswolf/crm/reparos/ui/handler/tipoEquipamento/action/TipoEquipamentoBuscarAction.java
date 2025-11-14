package com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.action;

import com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro.TipoEquipamentoFiltro;
import com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro.TipoEquipamentoFiltroService;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
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

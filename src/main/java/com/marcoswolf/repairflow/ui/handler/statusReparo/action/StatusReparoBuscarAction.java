package com.marcoswolf.repairflow.ui.handler.statusReparo.action;

import com.marcoswolf.repairflow.business.statusReparo.filtro.Filtro;
import com.marcoswolf.repairflow.business.statusReparo.filtro.FiltroService;
import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatusReparoBuscarAction {
    private final FiltroService statusReparoFiltroService;

    public List<StatusReparo> executar(String nome) {
        try {
            var filtro = new Filtro();
            filtro.setNome(nome);
            return statusReparoFiltroService.aplicarFiltros(filtro);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}

package com.marcoswolf.repairflow.ui.handler.reparo.action;

import com.marcoswolf.repairflow.business.reparo.filtro.ReparoFiltro;
import com.marcoswolf.repairflow.business.reparo.filtro.ReparoFiltroService;
import com.marcoswolf.repairflow.infrastructure.entities.Reparo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReparoBuscarAction {
    private final ReparoFiltroService reparoFiltroService;

    public List<Reparo> executar(String termo) {
        try {
            var filtro = new ReparoFiltro();
            filtro.setTermo(termo);
            return reparoFiltroService.aplicarFiltros(filtro);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}

package com.marcoswolf.crm.reparos.ui.handler.reparo.action;

import com.marcoswolf.crm.reparos.business.reparo.filtro.ReparoFiltro;
import com.marcoswolf.crm.reparos.business.reparo.filtro.ReparoFiltroService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
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

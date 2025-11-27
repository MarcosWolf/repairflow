package com.marcoswolf.repairflow.ui.handler.cliente.action;

import com.marcoswolf.repairflow.business.cliente.filtro.ClienteFiltro;
import com.marcoswolf.repairflow.business.cliente.filtro.ClienteFiltroService;
import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClienteBuscarAction {
    private final ClienteFiltroService clienteFiltroService;

    public List<Cliente> executar(String nome) {
        try {
            var filtro = new ClienteFiltro();
            filtro.setNome(nome);
            return clienteFiltroService.aplicarFiltros(filtro);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}

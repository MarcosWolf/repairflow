package com.marcoswolf.crm.reparos.ui.handler.cliente;

import com.marcoswolf.crm.reparos.business.cliente.IClienteConsultaService;
import com.marcoswolf.crm.reparos.business.cliente.filtro.ClienteFiltro;
import com.marcoswolf.crm.reparos.business.cliente.filtro.ClienteFiltroService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
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

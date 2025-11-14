package com.marcoswolf.crm.reparos.ui.handler.cliente.action;

import com.marcoswolf.crm.reparos.business.cliente.ClienteConsultaService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClienteCarregarAction {
    private final ClienteConsultaService clienteConsultaService;

    public List<Cliente> carregarTodos() {
        return clienteConsultaService.listarTodos();
    }
}

package com.marcoswolf.repairflow.ui.handler.cliente.action;

import com.marcoswolf.repairflow.business.cliente.ClienteConsultaService;
import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
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

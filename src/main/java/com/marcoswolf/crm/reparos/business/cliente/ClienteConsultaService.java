package com.marcoswolf.crm.reparos.business.cliente;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;

import java.util.List;

public interface ClienteConsultaService {
    List<Cliente> listarTodos();
}

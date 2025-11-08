package com.marcoswolf.crm.reparos.ui.handler.cliente;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;

public interface ClienteAction {
    boolean execute(Cliente clienteEditando, ClienteFormData data);
}

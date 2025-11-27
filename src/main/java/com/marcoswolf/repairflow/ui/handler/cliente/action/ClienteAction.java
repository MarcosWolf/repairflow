package com.marcoswolf.repairflow.ui.handler.cliente.action;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.ui.handler.cliente.dto.ClienteFormData;

public interface ClienteAction {
    boolean execute(Cliente cliente, ClienteFormData data);
}

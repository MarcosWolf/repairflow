package com.marcoswolf.repairflow.ui.handler.cliente.validator;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.ui.handler.cliente.dto.ClienteFormData;

public interface ClienteValidator {
    void validar(ClienteFormData data, Cliente novoCliente);
}
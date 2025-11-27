package com.marcoswolf.repairflow.business.cliente.filtro.strategy;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;

public interface ClienteFiltroStrategy {
    boolean aplicar(Cliente cliente);
}

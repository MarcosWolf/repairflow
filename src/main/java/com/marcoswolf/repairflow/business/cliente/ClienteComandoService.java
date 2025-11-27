package com.marcoswolf.repairflow.business.cliente;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;

public interface ClienteComandoService {
    void salvar(Cliente cliente);
    void deletar(Long id);
}

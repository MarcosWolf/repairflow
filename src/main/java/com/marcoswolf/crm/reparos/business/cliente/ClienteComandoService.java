package com.marcoswolf.crm.reparos.business.cliente;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;

public interface ClienteComandoService {
    void salvar(Cliente cliente);
    void deletar(Long id);
}

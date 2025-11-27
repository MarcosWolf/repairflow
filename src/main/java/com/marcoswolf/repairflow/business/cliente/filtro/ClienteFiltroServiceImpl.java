package com.marcoswolf.repairflow.business.cliente.filtro;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;

import java.util.List;

public interface ClienteFiltroServiceImpl {
    List<Cliente> aplicarFiltros(ClienteFiltro filtro);
    List<Cliente> aplicarFiltros(List<Cliente> clientes, ClienteFiltro filtro);
}

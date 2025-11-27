package com.marcoswolf.repairflow.business.cliente.filtro;

import com.marcoswolf.repairflow.business.cliente.filtro.strategy.*;
import com.marcoswolf.repairflow.business.cliente.filtro.strategy.*;
import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.repairflow.infrastructure.repositories.ReparoRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClienteFiltroService implements ClienteFiltroServiceImpl {
    private final ClienteRepository clienteRepository;
    private final ReparoRepository reparoRepository;

    public ClienteFiltroService(ClienteRepository clienteRepository, ReparoRepository reparoRepository) {
        this.clienteRepository = clienteRepository;
        this.reparoRepository = reparoRepository;
    }

    public List<Cliente> aplicarFiltros(ClienteFiltro filtro) {
        List<Cliente> clientes = clienteRepository.findAll();
        return aplicarFiltros(clientes, filtro);
    }

    public List<Cliente> aplicarFiltros(List<Cliente> clientes, ClienteFiltro filtro) {
        var strategies = new ArrayList<ClienteFiltroStrategy>();

        strategies.add(new FiltroNome(filtro.getNome()));

        if (filtro.isPendentes()) strategies.add(new FiltroPendentes(reparoRepository));
        if (filtro.isInativos()) strategies.add(new FiltroInativos(reparoRepository));
        if (filtro.isRecentes()) strategies.add(new FiltroRecentes(reparoRepository));

        return clientes.stream()
                .filter(c -> strategies.stream().allMatch(s -> s.aplicar(c)))
                .toList();
    }
}

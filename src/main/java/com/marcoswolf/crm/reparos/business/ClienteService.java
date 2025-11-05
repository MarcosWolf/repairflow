package com.marcoswolf.crm.reparos.business;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public void salvarCliente(Cliente cliente) {
        repository.saveAndFlush(cliente);
    }

    public List<Cliente> buscarPorNome(String nome) {
        var clientes = repository.findByNomeContainingIgnoreCase(nome);

        if (clientes.isEmpty()) {
            throw new RuntimeException("Nenhum Cliente encontrado");
        }

        return clientes;
    }
}

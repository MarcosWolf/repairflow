package com.marcoswolf.crm.reparos.business.cliente;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements ClienteConsultaService, ClienteComandoService {
    private final ClienteRepository clienteRepository;
    private final ReparoRepository reparoRepository;
    private final EquipamentoRepository equipamentoRepository;

    public ClienteService(ClienteRepository clienteRepository, ReparoRepository reparoRepository, EquipamentoRepository equipamentoRepository) {
        this.clienteRepository = clienteRepository;
        this.reparoRepository = reparoRepository;
        this.equipamentoRepository = equipamentoRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public void salvar(Cliente cliente) {
        clienteRepository.saveAndFlush(cliente);
    }

    public void deletar(Long id) {
        var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        clienteRepository.delete(cliente);
    }
}

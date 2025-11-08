package com.marcoswolf.crm.reparos.business.cliente;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Endereco;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ReparoRepository reparoRepository;

    public ClienteService(ClienteRepository clienteRepository, ReparoRepository reparoRepository) {
        this.clienteRepository = clienteRepository;
        this.reparoRepository = reparoRepository;
    }

    // Create
    public void salvarCliente(Cliente cliente) {
        clienteRepository.saveAndFlush(cliente);
    }

    // Read
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Cliente> filtrarClientes(ClienteFiltro filtro) {
        var clientes = clienteRepository.findAll();

        return clientes.stream()
                .filter(c -> {
                    if (filtro.getNome() == null || filtro.getNome().isBlank()) return true;
                    return c.getNome() != null && c.getNome().toLowerCase().contains(filtro.getNome().toLowerCase().trim());
                })

                .filter(c -> !filtro.isPendentes()
                        || reparoRepository.existsByEquipamento_Cliente_IdAndPagamento_PagoFalse(c.getId()))

                .filter(c -> !filtro.isInativos()
                        || !reparoRepository.existsByEquipamento_Cliente_IdAndDataEntradaAfter(
                        c.getId(), LocalDate.now().minusDays(90)))

                .filter(c -> !filtro.isRecentes()
                        || reparoRepository.existsByEquipamento_Cliente_IdAndDataEntradaAfter(
                        c.getId(), LocalDate.now().minusDays(30)))

                .filter(c -> !filtro.isComReparos()
                        || reparoRepository.existsByEquipamento_Cliente_IdAndConcluidoFalse(c.getId()))

                .collect(Collectors.toList());
    }

    // Update
    public Cliente atualizarCliente(Long id, Cliente novoCliente) {
        var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        cliente.setNome(novoCliente.getNome());
        cliente.setTelefone(novoCliente.getTelefone());
        cliente.setEmail(novoCliente.getEmail());

        if (novoCliente.getEndereco() != null) {
            if (cliente.getEndereco() != null) {
                cliente.setEndereco(new Endereco());
            }

            var endereco = cliente.getEndereco();
            var novoEndereco = novoCliente.getEndereco();

            endereco.setCidade(novoEndereco.getCidade());
            endereco.setEstado(novoEndereco.getEstado());
            endereco.setBairro(novoEndereco.getBairro());
            endereco.setLogradouro(novoEndereco.getLogradouro());
            endereco.setNumero(novoEndereco.getNumero());
            endereco.setCep(novoEndereco.getCep());
        }

        return clienteRepository.saveAndFlush(cliente);
    }

    // Delete
    public void deletarCliente(Long id) {
        var cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        boolean possuiReparos = !reparoRepository.findByEquipamento_Cliente_Id(id).isEmpty();

        if (possuiReparos) {
            throw new RuntimeException("Não é possível excluir o cliente: existe reparo associado.");
        }

        clienteRepository.delete(cliente);
    }
}

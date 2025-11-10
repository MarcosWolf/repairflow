package com.marcoswolf.crm.reparos.business.equipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService implements EquipamentoConsultaService, EquipamentoComandoService {
    private final EquipamentoRepository equipamentoRepository;
    private final ReparoRepository reparoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, ReparoRepository reparoRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.reparoRepository = reparoRepository;
    }

    // Create
    public void salvar(Equipamento equipamento) {
        equipamentoRepository.saveAndFlush(equipamento);
    }

    // Read
    public List<Equipamento> listarTodos() {
        return equipamentoRepository.findAll();
    }

    public List<Equipamento> listarPorClienteId(Long clienteId) {
        if (clienteId == null) return List.of();
        return equipamentoRepository.findByCliente_Id(clienteId);
    }

    public List<Equipamento> buscarPorNumeroSerie(String numeroSerie) {
        var equipamentos = equipamentoRepository.findByNumeroSerieContainingIgnoreCase(numeroSerie);

        if (equipamentos.isEmpty()) {
            throw new RuntimeException("Equipamento não encontrado.");
        }

        return equipamentos;
    }

    // Update
    public Equipamento atualizar(Long id, Equipamento novoEquipamento) {
        var equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado."));

        equipamento.setTipoEquipamento(novoEquipamento.getTipoEquipamento());
        equipamento.setCliente(novoEquipamento.getCliente());
        equipamento.setMarca(novoEquipamento.getMarca());
        equipamento.setModelo(novoEquipamento.getModelo());
        equipamento.setNumeroSerie(novoEquipamento.getNumeroSerie());
        equipamento.setCliente(novoEquipamento.getCliente());

        return equipamentoRepository.saveAndFlush(equipamento);
    }

    // Delete
    public void deletar(Long id) {
        var equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado."));

        boolean possuiReparos = !reparoRepository.findByEquipamento_Id(id).isEmpty();

        if (possuiReparos) {
            throw new RuntimeException("Não é possível excluir o equipamento: existe reparo associado.");
        }

        equipamentoRepository.delete(equipamento);
    }
}

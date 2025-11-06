package com.marcoswolf.crm.reparos.business;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService {
    private final EquipamentoRepository equipamentoRepository;
    private final ReparoRepository reparoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, ReparoRepository reparoRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.reparoRepository = reparoRepository;
    }

    // Create
    public void salvarEquipamento(Equipamento equipamento) {
        equipamentoRepository.saveAndFlush(equipamento);
    }

    // Read
    public List<Equipamento> buscarPorNumeroSerie(String numeroSerie) {
        var equipamentos = equipamentoRepository.findByNumeroSerieContainingIgnoreCase(numeroSerie);

        if (equipamentos.isEmpty()) {
            throw new RuntimeException("Nenhum Equipamento encontrado");
        }

        return equipamentos;
    }

    // Update
    public Equipamento atualizarEquipamento(Integer id, Equipamento novoEquipamento) {
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
    public void deletarEquipamento(Integer id) {
        var equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado."));

        boolean possuiReparos = !reparoRepository.findByEquipamento_Id(id).isEmpty();

        if (possuiReparos) {
            throw new RuntimeException("Não é possível excluir o equipamento: existem reparos associados.");
        }

        equipamentoRepository.delete(equipamento);
    }
}

package com.marcoswolf.crm.reparos.business;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.TipoEquipamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoEquipamentoService {
    private final TipoEquipamentoRepository tipoEquipamentoRepository;
    private final EquipamentoRepository equipamentoRepository;

    public TipoEquipamentoService(TipoEquipamentoRepository tipoEquipamentoRepository, EquipamentoRepository equipamentoRepository) {
        this.tipoEquipamentoRepository = tipoEquipamentoRepository;
        this.equipamentoRepository = equipamentoRepository;
    }

    // Create
    public void salvarTipoEquipamento(TipoEquipamento tipoEquipamento) {
        tipoEquipamentoRepository.saveAndFlush(tipoEquipamento);
    }

    // Read
    public List<TipoEquipamento> buscarPorNome(String nome) {
        var tipoEquipamentos = tipoEquipamentoRepository.findByNomeContainingIgnoreCase(nome);

        if (tipoEquipamentos.isEmpty()) {
            throw new RuntimeException("Tipo de equipamento não encontrado.");
        }

        return tipoEquipamentos;
    }

    // Update
    public TipoEquipamento atualizarTipoEquipamento(Integer id, TipoEquipamento novoTipoEquipamento) {
        var tipoEquipamento = tipoEquipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de equipamento não encontrado."));

        tipoEquipamento.setNome(novoTipoEquipamento.getNome());

        return tipoEquipamentoRepository.saveAndFlush(tipoEquipamento);
    }

    // Delete
    public void deletarTipoEquipamento(Integer id) {
        var tipoEquipamento = tipoEquipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de equipamento não encontrado."));

        boolean possuiEquipamento = !equipamentoRepository.findByTipoEquipamento_Id(id).isEmpty();

        if (possuiEquipamento) {
            throw new RuntimeException("Não é possível excluir o tipo de equipamento: existe equipamento associado.");
        }

        tipoEquipamentoRepository.delete(tipoEquipamento);
    }
}

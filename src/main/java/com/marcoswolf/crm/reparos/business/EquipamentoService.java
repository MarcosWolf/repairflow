package com.marcoswolf.crm.reparos.business;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService {
    private final EquipamentoRepository repository;

    public EquipamentoService(EquipamentoRepository repository) {
        this.repository = repository;
    }

    public void salvarEquipamento(Equipamento equipamento) {
        repository.saveAndFlush(equipamento);
    }

    public List<Equipamento> buscarPorNumeroSerie(String numeroSerie) {
        var equipamentos = repository.findByNumeroSerieContainingIgnoreCase(numeroSerie);

        if (equipamentos.isEmpty()) {
            throw new RuntimeException("Nenhum Equipamento encontrado");
        }

        return equipamentos;
    }
}

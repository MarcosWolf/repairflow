package com.marcoswolf.repairflow.business.equipamento.filtro;

import com.marcoswolf.repairflow.business.equipamento.filtro.strategy.EquipamentoFiltroStrategy;
import com.marcoswolf.repairflow.business.equipamento.filtro.strategy.FiltroNome;
import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.infrastructure.repositories.EquipamentoRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EquipamentoFiltroService implements IEquipamentoFiltroService {
    private final EquipamentoRepository equipamentoRepository;

    public EquipamentoFiltroService(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    public List<Equipamento> aplicarFiltros(EquipamentoFiltro filtro) {
        List<Equipamento> equipamentos = equipamentoRepository.findAll();
        return aplicarFiltros(equipamentos, filtro);
    }

    public List<Equipamento> aplicarFiltros(List<Equipamento> equipamentos, EquipamentoFiltro filtro) {
        var strategies = new ArrayList<EquipamentoFiltroStrategy>();

        if (filtro.getNome() != null && !filtro.getNome().isBlank()) {
            strategies.add(new FiltroNome(filtro.getNome()));
        }

        return equipamentos.stream()
                .filter(c -> strategies.stream().allMatch(s -> s.aplicar(c)))
                .toList();
    }
}

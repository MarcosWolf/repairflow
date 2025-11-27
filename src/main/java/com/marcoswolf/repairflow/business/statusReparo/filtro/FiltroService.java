package com.marcoswolf.repairflow.business.statusReparo.filtro;

import com.marcoswolf.repairflow.business.statusReparo.filtro.strategy.FiltroNome;
import com.marcoswolf.repairflow.business.statusReparo.filtro.strategy.FiltroStrategy;
import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import com.marcoswolf.repairflow.infrastructure.repositories.StatusReparoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FiltroService implements IFiltroService {
    private final StatusReparoRepository statusReparoRepository;

    public FiltroService(StatusReparoRepository statusReparoRepository) {
        this.statusReparoRepository = statusReparoRepository;
    }

    public List<StatusReparo> aplicarFiltros(Filtro filtro) {
        List<StatusReparo> statusReparos = statusReparoRepository.findAll();
        return aplicarFiltros(statusReparos, filtro);
    }

    public List<StatusReparo> aplicarFiltros(List<StatusReparo> statusReparos, Filtro filtro) {
        var strategies = new ArrayList<FiltroStrategy>();

        strategies.add(new FiltroNome(filtro.getNome()));

        return statusReparos.stream()
                .filter(c -> strategies.stream().allMatch(s -> s.aplicar(c)))
                .toList();
    }
}

package com.marcoswolf.repairflow.business.reparo.filtro;

import com.marcoswolf.repairflow.business.reparo.filtro.strategy.FiltroNome;
import com.marcoswolf.repairflow.business.reparo.filtro.strategy.ReparoFiltroStrategy;
import com.marcoswolf.repairflow.infrastructure.entities.Reparo;
import com.marcoswolf.repairflow.infrastructure.repositories.ReparoRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReparoFiltroService implements IReparoFiltroService {
    private final ReparoRepository reparoRepository;

    public ReparoFiltroService(ReparoRepository reparoRepository) {
        this.reparoRepository = reparoRepository;
    }

    public List<Reparo> aplicarFiltros(ReparoFiltro filtro) {
        List<Reparo> reparos = reparoRepository.findAll();
        return aplicarFiltros(reparos, filtro);
    }

    public List<Reparo> aplicarFiltros(List<Reparo> reparos, ReparoFiltro filtro) {
        var strategies = new ArrayList<ReparoFiltroStrategy>();;

        strategies.add(new FiltroNome(filtro.getTermo()));

        return reparos.stream()
                .filter(c -> strategies.stream().allMatch(s -> s.aplicar(c)))
                .toList();
    }
}

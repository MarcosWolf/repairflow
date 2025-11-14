package com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro;

import com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro.strategy.FiltroNome;
import com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro.strategy.ITipoEquipamentoFiltroStrategy;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.TipoEquipamentoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TipoEquipamentoFiltroService implements TipoEquipamentoFiltroServiceImpl {
    private final TipoEquipamentoRepository tipoEquipamentoRepository;

    public TipoEquipamentoFiltroService(TipoEquipamentoRepository tipoEquipamentoRepository) {
        this.tipoEquipamentoRepository = tipoEquipamentoRepository;
    }

    public List<TipoEquipamento> aplicarFiltros(TipoEquipamentoFiltro filtro) {
        List<TipoEquipamento> tipoEquipamentos = tipoEquipamentoRepository.findAll();
        return aplicarFiltros(tipoEquipamentos, filtro);
    }

    public List<TipoEquipamento> aplicarFiltros(List<TipoEquipamento> tipoEquipamentos, TipoEquipamentoFiltro filtro) {
        var strategies = new ArrayList<ITipoEquipamentoFiltroStrategy>();

        strategies.add(new FiltroNome(filtro.getNome()));

        /*
        if (filtro.isPendentes()) strategies.add(new FiltroPendentes(reparoRepository));
        if (filtro.isComReparos()) strategies.add(new FiltroComReparos(reparoRepository));
        if (filtro.isInativos()) strategies.add(new FiltroInativos(reparoRepository));
        if (filtro.isRecentes()) strategies.add(new FiltroRecentes(reparoRepository));
        */

        return tipoEquipamentos.stream()
                .filter(c -> strategies.stream().allMatch(s -> s.aplicar(c)))
                .toList();
    }
}
package com.marcoswolf.repairflow.business.reparo.filtro;

import com.marcoswolf.repairflow.infrastructure.entities.Reparo;

import java.util.List;

public interface IReparoFiltroService {
    List<Reparo> aplicarFiltros(ReparoFiltro filtro);
    List<Reparo> aplicarFiltros(List<Reparo> reparos, ReparoFiltro filtro);
}

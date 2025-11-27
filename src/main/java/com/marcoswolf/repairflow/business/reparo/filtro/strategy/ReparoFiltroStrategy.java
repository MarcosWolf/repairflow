package com.marcoswolf.repairflow.business.reparo.filtro.strategy;

import com.marcoswolf.repairflow.infrastructure.entities.Reparo;

public interface ReparoFiltroStrategy {
    boolean aplicar(Reparo reparo);
}

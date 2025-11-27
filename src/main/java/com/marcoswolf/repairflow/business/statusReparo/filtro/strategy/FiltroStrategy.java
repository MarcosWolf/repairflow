package com.marcoswolf.repairflow.business.statusReparo.filtro.strategy;

import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;

public interface FiltroStrategy {
    boolean aplicar(StatusReparo statusReparo);
}

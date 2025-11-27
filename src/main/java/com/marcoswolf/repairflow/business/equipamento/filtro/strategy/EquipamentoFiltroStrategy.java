package com.marcoswolf.repairflow.business.equipamento.filtro.strategy;

import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;

public interface EquipamentoFiltroStrategy {
    boolean aplicar(Equipamento equipamento);
}

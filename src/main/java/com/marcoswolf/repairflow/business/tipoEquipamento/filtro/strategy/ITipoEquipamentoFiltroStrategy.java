package com.marcoswolf.repairflow.business.tipoEquipamento.filtro.strategy;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;

public interface ITipoEquipamentoFiltroStrategy {
    boolean aplicar(TipoEquipamento tipoEquipamento);
}

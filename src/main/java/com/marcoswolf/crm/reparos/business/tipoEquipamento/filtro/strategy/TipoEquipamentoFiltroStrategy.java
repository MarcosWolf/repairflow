package com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro.strategy;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;

public interface TipoEquipamentoFiltroStrategy {
    boolean aplicar(TipoEquipamento tipoEquipamento);
}

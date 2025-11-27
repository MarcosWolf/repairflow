package com.marcoswolf.repairflow.ui.handler.tipoEquipamento.action;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;

public interface TipoEquipamentoAction {
    boolean execute(TipoEquipamento tipoEquipamento, TipoEquipamentoFormData data);
}

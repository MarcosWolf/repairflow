package com.marcoswolf.repairflow.ui.handler.equipamento.action;

import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.ui.handler.equipamento.dto.EquipamentoFormData;

public interface EquipamentoAction {
    boolean execute(Equipamento equipamento, EquipamentoFormData data);
}

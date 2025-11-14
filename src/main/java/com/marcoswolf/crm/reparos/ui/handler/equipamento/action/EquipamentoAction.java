package com.marcoswolf.crm.reparos.ui.handler.equipamento.action;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.ui.handler.equipamento.dto.EquipamentoFormData;

public interface EquipamentoAction {
    boolean execute(Equipamento equipamento, EquipamentoFormData data);
}

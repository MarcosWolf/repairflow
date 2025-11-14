package com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.action;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;

public interface TipoEquipamentoAction {
    boolean execute(TipoEquipamento tipoEquipamento, TipoEquipamentoFormData data);
}

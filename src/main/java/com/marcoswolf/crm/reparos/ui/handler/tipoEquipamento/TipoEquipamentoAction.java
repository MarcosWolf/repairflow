package com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;

public interface TipoEquipamentoAction {
    boolean execute(TipoEquipamento tipoEquipamento, TipoEquipamentoFormData data);
}

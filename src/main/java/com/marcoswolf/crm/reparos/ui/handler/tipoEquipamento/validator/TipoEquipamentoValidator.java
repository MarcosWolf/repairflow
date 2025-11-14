package com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.validator;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;

public interface TipoEquipamentoValidator {
    void validar(TipoEquipamentoFormData data, TipoEquipamento novoTipoEquipamento);
}

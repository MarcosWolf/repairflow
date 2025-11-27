package com.marcoswolf.repairflow.ui.handler.tipoEquipamento.validator;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;

public interface TipoEquipamentoValidator {
    void validar(TipoEquipamentoFormData data, TipoEquipamento novoTipoEquipamento);
}

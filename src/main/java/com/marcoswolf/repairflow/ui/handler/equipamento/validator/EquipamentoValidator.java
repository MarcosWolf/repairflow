package com.marcoswolf.repairflow.ui.handler.equipamento.validator;

import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.ui.handler.equipamento.dto.EquipamentoFormData;

public interface EquipamentoValidator {
    void validar(EquipamentoFormData data, Equipamento novoEquipamento);
}

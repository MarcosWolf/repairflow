package com.marcoswolf.crm.reparos.ui.handler.equipamento.validator;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.ui.handler.equipamento.dto.EquipamentoFormData;

public interface EquipamentoValidator {
    void validar(EquipamentoFormData data, Equipamento novoEquipamento);
}

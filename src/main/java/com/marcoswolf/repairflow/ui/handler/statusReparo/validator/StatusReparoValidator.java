package com.marcoswolf.repairflow.ui.handler.statusReparo.validator;

import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import com.marcoswolf.repairflow.ui.handler.statusReparo.dto.StatusReparoFormData;

public interface StatusReparoValidator {
    void validar(StatusReparoFormData data, StatusReparo novoStatusReparo);
}

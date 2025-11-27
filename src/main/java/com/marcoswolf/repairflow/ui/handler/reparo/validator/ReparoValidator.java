package com.marcoswolf.repairflow.ui.handler.reparo.validator;

import com.marcoswolf.repairflow.infrastructure.entities.Reparo;
import com.marcoswolf.repairflow.ui.handler.reparo.dto.ReparoFormData;

public interface ReparoValidator {
    void validar(ReparoFormData data, Reparo reparo);
}

package com.marcoswolf.crm.reparos.ui.handler.reparo.validator;

import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import com.marcoswolf.crm.reparos.ui.handler.reparo.dto.ReparoFormData;

public interface ReparoValidator {
    void validar(ReparoFormData data, Reparo reparo);
}

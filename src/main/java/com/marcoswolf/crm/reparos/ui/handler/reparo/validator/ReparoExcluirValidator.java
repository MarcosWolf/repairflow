package com.marcoswolf.crm.reparos.ui.handler.reparo.validator;

import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import com.marcoswolf.crm.reparos.ui.handler.reparo.dto.ReparoFormData;
import org.springframework.stereotype.Component;

@Component
public class ReparoExcluirValidator implements ReparoValidator {
    public ReparoExcluirValidator() {
    }

    @Override
    public void validar(ReparoFormData data, Reparo reparo) {

    }
}

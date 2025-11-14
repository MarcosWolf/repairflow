package com.marcoswolf.crm.reparos.ui.handler.reparo.action;

import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import com.marcoswolf.crm.reparos.ui.handler.reparo.dto.ReparoFormData;

public interface ReparoAction {
    boolean execute(Reparo reparo, ReparoFormData data);
}

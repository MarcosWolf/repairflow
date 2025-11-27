package com.marcoswolf.repairflow.ui.handler.reparo.action;

import com.marcoswolf.repairflow.infrastructure.entities.Reparo;
import com.marcoswolf.repairflow.ui.handler.reparo.dto.ReparoFormData;

public interface ReparoAction {
    boolean execute(Reparo reparo, ReparoFormData data);
}

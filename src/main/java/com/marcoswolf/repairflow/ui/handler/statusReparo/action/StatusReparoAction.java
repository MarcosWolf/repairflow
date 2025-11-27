package com.marcoswolf.repairflow.ui.handler.statusReparo.action;

import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import com.marcoswolf.repairflow.ui.handler.statusReparo.dto.StatusReparoFormData;

public interface StatusReparoAction {
    boolean execute(StatusReparo statusReparo, StatusReparoFormData statusReparoFormData);
}

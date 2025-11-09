package com.marcoswolf.crm.reparos.ui.handler.statusReparo;

import com.marcoswolf.crm.reparos.infrastructure.entities.StatusReparo;

public interface StatusReparoAction {
    boolean execute(StatusReparo statusReparo, StatusReparoFormData statusReparoFormData);
}

package com.marcoswolf.crm.reparos.ui.handler.statusReparo;

import com.marcoswolf.crm.reparos.ui.handler.shared.IFormData;

public record StatusReparoFormData(
        String nome
) implements IFormData {}

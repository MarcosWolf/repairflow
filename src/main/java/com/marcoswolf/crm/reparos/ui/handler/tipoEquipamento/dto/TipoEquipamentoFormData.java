package com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.dto;

import com.marcoswolf.crm.reparos.ui.handler.shared.IFormData;

public record TipoEquipamentoFormData (
    String nome
) implements IFormData {}

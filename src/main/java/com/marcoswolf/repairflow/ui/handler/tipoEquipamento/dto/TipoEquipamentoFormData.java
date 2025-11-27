package com.marcoswolf.repairflow.ui.handler.tipoEquipamento.dto;

import com.marcoswolf.repairflow.ui.handler.shared.IFormData;

public record TipoEquipamentoFormData (
    String nome
) implements IFormData {}

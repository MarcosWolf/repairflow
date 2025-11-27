package com.marcoswolf.repairflow.ui.handler.equipamento.dto;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.repairflow.ui.handler.shared.IFormData;

public record EquipamentoFormData (
    TipoEquipamento tipoEquipamento,
    String marca,
    String modelo,
    String numeroSerie,
    Cliente cliente
) implements IFormData {}

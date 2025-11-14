package com.marcoswolf.crm.reparos.ui.handler.equipamento.dto;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.ui.handler.shared.IFormData;

public record EquipamentoFormData (
    TipoEquipamento tipoEquipamento,
    String marca,
    String modelo,
    String numeroSerie,
    Cliente cliente
) implements IFormData {}

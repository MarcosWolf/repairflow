package com.marcoswolf.crm.reparos.controller.dto;

public record EquipamentoRequestTestDTO(
        Long tipoEquipamentoId,
        String marca,
        String modelo,
        String numeroSerie,
        Long clienteId
) {}

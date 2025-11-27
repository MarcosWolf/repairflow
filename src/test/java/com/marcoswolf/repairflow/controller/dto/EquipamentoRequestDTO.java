package com.marcoswolf.repairflow.controller.dto;

public record EquipamentoRequestDTO(
        Long tipoEquipamentoId,
        String marca,
        String modelo,
        String numeroSerie,
        Long clienteId
) {}
package com.marcoswolf.repairflow.controller.dto;

import java.math.BigDecimal;

public record PecaPagamentoRequestDTO (
        Long pecaId,
        String nome,
        Integer quantidade,
        BigDecimal valorUnitario
) {}

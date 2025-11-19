package com.marcoswolf.crm.reparos.controller.dto;

import java.math.BigDecimal;

public record PecaPagamentoRequestDTO (
        Long pecaId,
        Integer quantidade,
        BigDecimal valorUnitario
) {}

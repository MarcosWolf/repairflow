package com.marcoswolf.crm.reparos.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ReparoRequestDTO (
        Long equipamentoId,
        LocalDate dataEntrada,
        LocalDate dataSaida,
        String descricaoProblema,
        String servicoExecutado,
        Long statusReparoId,
        BigDecimal valorServico,
        BigDecimal desconto,
        LocalDate dataPagamento,
        List<PecaPagamentoRequestDTO> pecas
) {}

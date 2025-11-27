package com.marcoswolf.repairflow.ui.handler.reparo.dto;

import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.infrastructure.entities.PecaPagamento;
import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import com.marcoswolf.repairflow.ui.handler.shared.IFormData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ReparoFormData(
        Equipamento equipamento,
        LocalDate dataEntrada,
        LocalDate dataSaida,
        String descricaoProblema,
        String servicoExecutado,
        StatusReparo status,
        BigDecimal valorServico,
        BigDecimal desconto,
        LocalDate dataPagamento,
        List<PecaPagamento> pecas
) implements IFormData {}

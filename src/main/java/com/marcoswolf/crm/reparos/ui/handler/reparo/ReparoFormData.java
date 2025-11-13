package com.marcoswolf.crm.reparos.ui.handler.reparo;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.infrastructure.entities.PecaPagamento;
import com.marcoswolf.crm.reparos.infrastructure.entities.StatusReparo;
import com.marcoswolf.crm.reparos.ui.handler.shared.IFormData;

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

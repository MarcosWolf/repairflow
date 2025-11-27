package com.marcoswolf.repairflow.ui.handler.reparo.validator;

import com.marcoswolf.repairflow.infrastructure.entities.PecaPagamento;
import com.marcoswolf.repairflow.infrastructure.entities.Reparo;
import com.marcoswolf.repairflow.ui.handler.reparo.dto.ReparoFormData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.marcoswolf.repairflow.ui.utils.ValidationUtils.isEmpty;

@Component
public class ReparoSalvarValidator implements ReparoValidator {

    @Override
    public void validar(ReparoFormData data, Reparo novoReparo) {
        if (isEmpty(data.equipamento()) || isEmpty(data.equipamento().getId())) {
            throw new IllegalArgumentException("O campo equipamento é obrigatório.");
        }

        if (isEmpty(data.status()) || isEmpty(data.status().getId())) {
            throw new IllegalArgumentException("O campo status é obrigatório.");
        }

        if (isEmpty(data.dataEntrada())) {
            throw new IllegalArgumentException("O campo data de entrada é obrigatório.");
        }

        if (isEmpty(data.descricaoProblema())) {
            throw new IllegalArgumentException("O campo descrição do defeito é obrigatório.");
        }

        if (!validarValorTotal(data)) {
            throw new IllegalArgumentException("O valor total não pode ser negativo.");
        }
    }

    private boolean validarValorTotal(ReparoFormData data) {
        BigDecimal valorServico = data.valorServico() != null ? data.valorServico() : BigDecimal.ZERO;
        BigDecimal desconto = data.desconto() != null ? data.desconto() : BigDecimal.ZERO;

        BigDecimal totalPecas = data.pecas() != null
                ? data.pecas().stream()
                .map(PecaPagamento::getTotalLinha)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                : BigDecimal.ZERO;

        BigDecimal valorTotal = valorServico.add(totalPecas).subtract(desconto);

        return valorTotal.compareTo(BigDecimal.ZERO) >= 0;
    }
}

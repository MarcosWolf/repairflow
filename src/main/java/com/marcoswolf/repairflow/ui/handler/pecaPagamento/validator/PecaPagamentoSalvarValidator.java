package com.marcoswolf.repairflow.ui.handler.pecaPagamento.validator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PecaPagamentoSalvarValidator {

    public void validar(String descricao, Integer quantidade, BigDecimal valor) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição da peça não pode ser vazia.");
        }

        if (quantidade == null) {
            throw new IllegalArgumentException("A quantidade não pode ser vazia.");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }

        if (valor == null) {
            throw new IllegalArgumentException("Valor unitário inválido.");
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0 ) {
            throw new IllegalArgumentException("Valor unitário deve ser maior que zero.");
        }
    }
}

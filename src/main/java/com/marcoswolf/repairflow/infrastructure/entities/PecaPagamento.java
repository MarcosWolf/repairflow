package com.marcoswolf.repairflow.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

@Entity
@Table(name = "peca_pagamento")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PecaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private BigDecimal valor;
    private Integer quantidade;

    public static final NumberFormat FORMATADOR_MOEDA =
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public BigDecimal getTotalLinha() {
        if (valor == null || quantidade == null) return BigDecimal.ZERO;
        return valor.multiply(BigDecimal.valueOf(quantidade));
    }

    public String getValorFormatado() {
        return FORMATADOR_MOEDA.format(valor);
    }

    public String getTotalLinhaFormatado() {
        return FORMATADOR_MOEDA.format(getTotalLinha());
    }
}

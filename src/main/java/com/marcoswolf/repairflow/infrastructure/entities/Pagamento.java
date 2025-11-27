package com.marcoswolf.repairflow.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pagamento")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valorServico;
    private BigDecimal desconto;
    private LocalDate dataPagamento;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pagamento_id")
    private List<PecaPagamento> pecas;

    @OneToOne(mappedBy = "pagamento")
    @JsonBackReference
    private Reparo reparo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private BigDecimal valorTotal;

    public BigDecimal getValorTotal() {
        BigDecimal totalPecas = BigDecimal.ZERO;

        if (pecas != null) {
            for (PecaPagamento p : pecas) {
                BigDecimal quantidade = new BigDecimal(p.getQuantidade());
                totalPecas = totalPecas.add(p.getValor().multiply(quantidade));
            }
        }

        BigDecimal total = (valorServico != null ? valorServico : BigDecimal.ZERO)
                .add(totalPecas);

        if (desconto != null) {
            total = total.subtract(desconto);
        }

        return total;
    }
}

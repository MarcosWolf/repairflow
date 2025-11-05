package com.marcoswolf.crm.reparos.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reparo")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Reparo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private String descricaoProblema;
    private String servicoExecutado;
    private Double Valor;
    private String Status;

    @ManyToOne
    @JoinColumn(name = "equipamento_id")
    private Equipamento equipamento;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusReparo status;
}

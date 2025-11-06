package com.marcoswolf.crm.reparos.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "equipamento")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private TipoEquipamento tipoEquipamento;

    private String marca;
    private String modelo;
    private String numeroSerie;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
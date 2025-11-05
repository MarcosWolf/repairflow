package com.marcoswolf.crm.reparos.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_equipamento")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TipoEquipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;
}

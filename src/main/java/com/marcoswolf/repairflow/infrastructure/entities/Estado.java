package com.marcoswolf.repairflow.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String nome;
}

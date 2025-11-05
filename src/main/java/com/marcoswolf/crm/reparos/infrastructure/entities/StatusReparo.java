package com.marcoswolf.crm.reparos.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "status_reparo")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class StatusReparo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;
}

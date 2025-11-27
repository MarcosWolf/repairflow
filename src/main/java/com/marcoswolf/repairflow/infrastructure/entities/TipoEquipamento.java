package com.marcoswolf.repairflow.infrastructure.entities;

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

    @Transient
    private Long totalClientes;

    @Transient
    private Long totalEquipamentos;

    @Transient
    private Long totalReparos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoEquipamento)) return false;
        TipoEquipamento that = (TipoEquipamento) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

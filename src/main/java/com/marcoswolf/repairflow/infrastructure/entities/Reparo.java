package com.marcoswolf.repairflow.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reparo")
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "equipamento_id")
    private Equipamento equipamento;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusReparo status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pagamento_id")
    @JsonManagedReference
    private Pagamento pagamento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reparo)) return false;
        Reparo that = (Reparo) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

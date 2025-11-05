package com.marcoswolf.crm.reparos.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "cidade", column = @Column(name = "endereco_cidade")),
            @AttributeOverride(name = "estado", column = @Column(name = "endereco_estado")),
            @AttributeOverride(name = "bairro", column = @Column(name = "endereco_bairro")),
            @AttributeOverride(name = "logradouro", column = @Column(name = "endereco_logradouro")),
            @AttributeOverride(name = "numero", column = @Column(name = "endereco_numero")),
            @AttributeOverride(name = "cep", column = @Column(name = "endereco_cep"))
    })
    private Endereco endereco;
}

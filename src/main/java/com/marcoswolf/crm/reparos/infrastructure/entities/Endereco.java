package com.marcoswolf.crm.reparos.infrastructure.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Endereco {
    private String cidade;
    private String estado;
    private String bairro;
    private String logradouro;
    private Integer numero;

    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido. Use 12345-678 ou 12345678")
    @Size(max = 9)
    @Column(name = "cep", length = 9)
    private String cep;
}
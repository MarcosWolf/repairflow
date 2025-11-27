package com.marcoswolf.repairflow.controller.dto;

public record ClienteRequestDTO(
        String nome,
        String telefone,
        String email,
        EnderecoDTO endereco
) {
    public record EnderecoDTO(
            String logradouro,
            int numero,
            String bairro,
            String cidade,
            String cep,
            Long estadoId
    ) {}
}

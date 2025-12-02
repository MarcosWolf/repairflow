package com.marcoswolf.repairflow.controller.mappers;

import com.marcoswolf.repairflow.controller.dto.ClienteRequestDTO;
import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.infrastructure.entities.Endereco;
import com.marcoswolf.repairflow.infrastructure.entities.Estado;
import com.marcoswolf.repairflow.infrastructure.repositories.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteRequestMapper {
    private final EstadoRepository estadoRepository;

    public Cliente toEntity(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setEmail(dto.email());
        cliente.setDocumento(dto.documento());

        var enderecoDTO = dto.endereco();
        Endereco endereco = new Endereco();
        endereco.setCidade(enderecoDTO.cidade());
        endereco.setBairro(enderecoDTO.bairro());
        endereco.setCep(enderecoDTO.cep());
        endereco.setLogradouro(enderecoDTO.logradouro());
        endereco.setNumero(enderecoDTO.numero());

        Estado estado = estadoRepository.findById(enderecoDTO.estadoId())
                .orElseThrow();

        endereco.setEstado(estado);
        cliente.setEndereco(endereco);

        return cliente;
    }
}

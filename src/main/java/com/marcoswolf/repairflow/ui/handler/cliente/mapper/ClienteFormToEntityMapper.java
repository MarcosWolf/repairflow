package com.marcoswolf.repairflow.ui.handler.cliente.mapper;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.ui.handler.cliente.dto.ClienteFormData;
import com.marcoswolf.repairflow.ui.mappers.ClienteFormMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteFormToEntityMapper {
    private final ClienteFormMapper delegate;

    public Cliente map(ClienteFormData data, Cliente novoCliente) {
        Cliente cliente = delegate.toEntity(
                data.nome(), data.telefone(), data.email(),
                data.cidade(), data.bairro(), data.cep(),
                data.logradouro(), data.numero(), data.estadoSelecionado()
        );

        if (novoCliente != null) {
            cliente.setId(novoCliente.getId());
        }
        return cliente;
    }
}
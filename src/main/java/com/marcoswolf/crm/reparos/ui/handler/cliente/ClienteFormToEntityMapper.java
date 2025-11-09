package com.marcoswolf.crm.reparos.ui.handler.cliente;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.mappers.ClienteFormMapper;
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
package com.marcoswolf.crm.reparos.ui.handler.cliente.validator;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import static com.marcoswolf.crm.reparos.ui.utils.ValidationUtils.isEmpty;

import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.crm.reparos.ui.handler.cliente.dto.ClienteFormData;
import org.springframework.stereotype.Component;

@Component
public class ClienteFormValidator implements ClienteValidator {

    private final ClienteRepository clienteRepository;

    public ClienteFormValidator(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void validar(ClienteFormData data, Cliente novoCliente) {
        if (isEmpty(data.nome())) {
            throw new IllegalArgumentException("O campo Nome é obrigatório.");
        }

        if (isEmpty(data.telefone())) {
            throw new IllegalArgumentException("O campo Telefone é obrigatório.");
        }

        Long id = novoCliente != null ? novoCliente.getId() : null;

        if (clienteRepository.existsByTelefoneAndNotId(data.telefone(), id)) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este telefone.");
        }

        if (!isEmpty(data.email()) && clienteRepository.existsByEmailAndNotId(data.email(), id)) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este e-mail.");
        }

        if (isEmpty(data.cidade())) {
            throw new IllegalArgumentException("O campo Cidade é obrigatório.");
        }

        Estado estado = data.estadoSelecionado();
        if (estado == null || estado.getId() == 0) {
            throw new IllegalArgumentException("O campo Estado é obrigatório.");
        }
    }
}
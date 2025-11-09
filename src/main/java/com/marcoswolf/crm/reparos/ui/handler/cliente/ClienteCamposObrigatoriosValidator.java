package com.marcoswolf.crm.reparos.ui.handler.cliente;

import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import static com.marcoswolf.crm.reparos.ui.utils.ValidationUtils.isEmpty;

import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import org.springframework.stereotype.Component;

@Component
public class ClienteCamposObrigatoriosValidator implements ClienteValidator {

    private final ClienteRepository clienteRepository;

    public ClienteCamposObrigatoriosValidator(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void validar(ClienteFormData data) {
        if (isEmpty(data.nome())) {
            throw new IllegalArgumentException("O campo Nome é obrigatório.");
        }

        if (isEmpty(data.telefone())) {
            throw new IllegalArgumentException("O campo Telefone é obrigatório.");
        }

        if (clienteRepository.existsByTelefone(data.telefone())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este telefone.");
        }

        if (!isEmpty(data.email()) && clienteRepository.existsByEmail(data.email())) {
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
package com.marcoswolf.crm.reparos.ui.handler.cliente.validator;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import static com.marcoswolf.crm.reparos.ui.utils.ValidationUtils.isEmpty;

import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.crm.reparos.ui.handler.cliente.dto.ClienteFormData;
import org.springframework.stereotype.Component;

@Component
public class ClienteSalvarValidator implements ClienteValidator {

    private final ClienteRepository clienteRepository;

    public ClienteSalvarValidator(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void validar(ClienteFormData data, Cliente novoCliente) {
        String cep = data.cep().replaceAll("\\D", "");
        String telefone = data.telefone().replaceAll("\\D", "");

        if (isEmpty(data.nome())) {
            throw new IllegalArgumentException("O campo nome é obrigatório.");
        }

        if (isEmpty(telefone)) {
            throw new IllegalArgumentException("O campo telefone é obrigatório.");
        }

        if (!isEmpty(telefone) && telefone.length() != 10 && telefone.length() != 11) {
            throw new IllegalArgumentException("O telefone é inválido.");
        }

        if (data.email() != null && !data.email().trim().isEmpty()) {
            if (!data.email().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                throw new IllegalArgumentException("O e-mail é inválido.");
            }
        }

        Long id = novoCliente != null ? novoCliente.getId() : null;

        if (clienteRepository.existsByTelefoneAndNotId(data.telefone(), id)) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este telefone.");
        }

        if (!isEmpty(data.email()) && clienteRepository.existsByEmailAndNotId(data.email(), id)) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este e-mail.");
        }

        if (isEmpty(data.cidade())) {
            throw new IllegalArgumentException("O campo cidade é obrigatório.");
        }

        Estado estado = data.estadoSelecionado();
        if (estado == null || estado.getId() == 0) {
            throw new IllegalArgumentException("O campo estado é obrigatório.");
        }

        // CEP precisa ter 8 caracteres
        if (cep != null && !cep.isEmpty() && cep.length() != 8) {
            throw new IllegalArgumentException("O CEP é inválido.");
        }
    }
}
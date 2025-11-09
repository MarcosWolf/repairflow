package com.marcoswolf.crm.reparos.ui.handler.statusReparo;

import com.marcoswolf.crm.reparos.infrastructure.entities.StatusReparo;
import com.marcoswolf.crm.reparos.infrastructure.repositories.StatusReparoRepository;
import org.springframework.stereotype.Component;

import static com.marcoswolf.crm.reparos.ui.utils.ValidationUtils.isEmpty;

@Component
public class StatusReparoCamposObrigatoriosValidator implements StatusReparoValidator {

    private final StatusReparoRepository statusReparoRepository;

    public StatusReparoCamposObrigatoriosValidator(StatusReparoRepository statusReparoRepository) {
        this.statusReparoRepository = statusReparoRepository;
    }

    @Override
    public void validar(StatusReparoFormData data, StatusReparo novoStatusReparo) {
        if (isEmpty(data.nome())) {
            throw new IllegalArgumentException("O campo nome é obrigatório.");
        }

        Long id = novoStatusReparo != null ? novoStatusReparo.getId() : null;

        if (!isEmpty(data.nome()) && statusReparoRepository.existsByNomeAndNotId(data.nome(), id)) {
            throw new IllegalArgumentException("Já existe um status de reparo cadastrado com este nome.");
        }
    }
}

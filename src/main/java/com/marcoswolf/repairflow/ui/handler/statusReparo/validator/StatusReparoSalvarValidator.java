package com.marcoswolf.repairflow.ui.handler.statusReparo.validator;

import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import com.marcoswolf.repairflow.infrastructure.repositories.StatusReparoRepository;
import com.marcoswolf.repairflow.ui.handler.statusReparo.dto.StatusReparoFormData;
import org.springframework.stereotype.Component;

import static com.marcoswolf.repairflow.ui.utils.ValidationUtils.isEmpty;

@Component
public class StatusReparoSalvarValidator implements StatusReparoValidator {

    private final StatusReparoRepository statusReparoRepository;

    public StatusReparoSalvarValidator(StatusReparoRepository statusReparoRepository) {
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

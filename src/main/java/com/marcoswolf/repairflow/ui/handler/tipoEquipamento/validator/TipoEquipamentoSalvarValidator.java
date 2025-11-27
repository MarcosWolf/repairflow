package com.marcoswolf.repairflow.ui.handler.tipoEquipamento.validator;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.repairflow.infrastructure.repositories.TipoEquipamentoRepository;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;
import org.springframework.stereotype.Component;

import static com.marcoswolf.repairflow.ui.utils.ValidationUtils.isEmpty;

@Component
public class TipoEquipamentoSalvarValidator implements TipoEquipamentoValidator {
    private final TipoEquipamentoRepository tipoEquipamentoRepository;

    public TipoEquipamentoSalvarValidator(TipoEquipamentoRepository tipoEquipamentoRepository) {
        this.tipoEquipamentoRepository = tipoEquipamentoRepository;
    }

    @Override
    public void validar(TipoEquipamentoFormData data, TipoEquipamento novoTipoEquipamento) {
        if (isEmpty(data.nome())) {
            throw new IllegalArgumentException("O campo nome é obrigatório.");
        }

        Long id = novoTipoEquipamento != null ? novoTipoEquipamento.getId() : null;

        if (!isEmpty(data.nome()) && tipoEquipamentoRepository.existsByNomeAndNotId(data.nome(), id)) {
            throw new IllegalArgumentException("Já existe um tipo de equipamento cadastrado com este nome.");
        }
    }
}

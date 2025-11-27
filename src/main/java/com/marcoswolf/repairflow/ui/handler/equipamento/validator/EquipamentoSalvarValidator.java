package com.marcoswolf.repairflow.ui.handler.equipamento.validator;

import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.repairflow.ui.handler.equipamento.dto.EquipamentoFormData;
import org.springframework.stereotype.Component;

import static com.marcoswolf.repairflow.ui.utils.ValidationUtils.isEmpty;

@Component
public class EquipamentoSalvarValidator implements EquipamentoValidator {
    private final EquipamentoRepository equipamentoRepository;

    public EquipamentoSalvarValidator(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    @Override
    public void validar(EquipamentoFormData data, Equipamento novoEquipamento) {
        if (isEmpty(data.tipoEquipamento()) || isEmpty(data.tipoEquipamento().getId())) {
            throw new IllegalArgumentException("O campo tipo de equipamento é obrigatório.");
        }

        if (isEmpty(data.marca())) {
            throw new IllegalArgumentException("O campo marca é obrigatório.");
        }

        if (isEmpty(data.modelo())) {
            throw new IllegalArgumentException("O campo modelo é obrigatório.");
        }

        if (isEmpty(data.cliente()) || isEmpty(data.cliente().getId())) {
            throw new IllegalArgumentException("O campo cliente é obrigatório.");
        }
    }
}

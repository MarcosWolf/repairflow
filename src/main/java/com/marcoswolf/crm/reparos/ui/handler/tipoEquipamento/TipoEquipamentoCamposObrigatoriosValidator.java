package com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento;

import com.marcoswolf.crm.reparos.infrastructure.repositories.TipoEquipamentoRepository;
import org.springframework.stereotype.Component;

import static com.marcoswolf.crm.reparos.ui.utils.ValidationUtils.isEmpty;

@Component
public class TipoEquipamentoCamposObrigatoriosValidator implements TipoEquipamentoValidator {

    private final TipoEquipamentoRepository tipoEquipamentoRepository;

    public TipoEquipamentoCamposObrigatoriosValidator(TipoEquipamentoRepository tipoEquipamentoRepository) {
        this.tipoEquipamentoRepository = tipoEquipamentoRepository;
    }

    @Override
    public void validar(TipoEquipamentoFormData data) {
        if (isEmpty(data.nome())) {
            throw new IllegalArgumentException("O campo Nome é obrigatório.");
        }

        if (!isEmpty(data.nome()) && tipoEquipamentoRepository.existsByNome(data.nome())) {
            throw new IllegalArgumentException("Já existe um tipo de equipamento cadastrado com este nome.");
        }
    }
}

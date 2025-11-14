package com.marcoswolf.crm.reparos.ui.handler.reparo.validator;

import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import com.marcoswolf.crm.reparos.ui.handler.reparo.dto.ReparoFormData;
import org.springframework.stereotype.Component;

import static com.marcoswolf.crm.reparos.ui.utils.ValidationUtils.isEmpty;

@Component
public class ReparoSalvarValidator implements ReparoValidator {

    @Override
    public void validar(ReparoFormData data, Reparo novoReparo) {
        if (isEmpty(data.equipamento()) || isEmpty(data.equipamento().getId())) {
            throw new IllegalArgumentException("O campo equipamento é obrigatório.");
        }

        if (isEmpty(data.status()) || isEmpty(data.status().getId())) {
            throw new IllegalArgumentException("O campo status é obrigatório.");
        }

        if (isEmpty(data.dataEntrada())) {
            throw new IllegalArgumentException("O campo data de entrada é obrigatório.");
        }

        if (isEmpty(data.descricaoProblema())) {
            throw new IllegalArgumentException("O campo descrição do defeito é obrigatório.");
        }
    }
}

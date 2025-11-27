package com.marcoswolf.repairflow.ui.handler.equipamento.validator;

import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.infrastructure.repositories.ReparoRepository;
import com.marcoswolf.repairflow.ui.handler.equipamento.dto.EquipamentoFormData;
import org.springframework.stereotype.Component;

@Component
public class EquipamentoExcluirValidator implements EquipamentoValidator {
    private final ReparoRepository reparoRepository;

    public EquipamentoExcluirValidator(ReparoRepository reparoRepository) {
        this.reparoRepository = reparoRepository;
    }

    @Override
    public void validar(EquipamentoFormData data, Equipamento equipamento) {
        if (reparoRepository.existsByEquipamento_Id(equipamento.getId())) {
            throw new IllegalArgumentException(
                    "Não é possível excluir o equipamento: existe reparo associado."
            );
        }
    }
}

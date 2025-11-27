package com.marcoswolf.repairflow.ui.handler.tipoEquipamento.validator;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.repairflow.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;
import org.springframework.stereotype.Component;

@Component
public class TipoEquipamentoExcluirValidator implements TipoEquipamentoValidator {
    private final EquipamentoRepository equipamentoRepository;

    public TipoEquipamentoExcluirValidator(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    @Override
    public void validar(TipoEquipamentoFormData data, TipoEquipamento tipoEquipamento) {
        if (equipamentoRepository.existsByTipoEquipamentoId(tipoEquipamento.getId())) {
            throw new IllegalArgumentException(
                    "Não é possível excluir o tipo de equipamento: existe equipamento associado."
            );
        }
    }
}

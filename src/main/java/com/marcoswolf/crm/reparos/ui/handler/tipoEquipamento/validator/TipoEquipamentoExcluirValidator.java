package com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.validator;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;
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

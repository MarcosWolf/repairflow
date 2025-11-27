package com.marcoswolf.repairflow.ui.handler.tipoEquipamento.mapper;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;
import com.marcoswolf.repairflow.ui.mappers.TipoEquipamentoFormMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TipoEquipamentoFormToEntityMapper {
    private final TipoEquipamentoFormMapper delegate;

    public TipoEquipamento map(TipoEquipamentoFormData data, TipoEquipamento novoTipoEquipamento) {
        TipoEquipamento tipoEquipamento = delegate.toEntity(
                data.nome()
        );

        if (novoTipoEquipamento != null) {
            tipoEquipamento.setId(novoTipoEquipamento.getId());
        }

        return tipoEquipamento;
    }
}
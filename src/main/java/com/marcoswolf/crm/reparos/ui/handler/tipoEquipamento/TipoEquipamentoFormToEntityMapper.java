package com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.ui.mappers.TipoEquipamentoFormMapper;
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
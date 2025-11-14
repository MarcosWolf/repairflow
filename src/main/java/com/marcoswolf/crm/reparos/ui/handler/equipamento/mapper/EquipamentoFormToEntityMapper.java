package com.marcoswolf.crm.reparos.ui.handler.equipamento.mapper;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.ui.handler.equipamento.dto.EquipamentoFormData;
import com.marcoswolf.crm.reparos.ui.mappers.EquipamentoFormMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipamentoFormToEntityMapper {

    private final EquipamentoFormMapper delegate;

    public Equipamento map(EquipamentoFormData data, Equipamento novoEquipamento) {
        Equipamento equipamento = delegate.toEntity(
                data.tipoEquipamento(),
                data.marca(),
                data.modelo(),
                data.numeroSerie(),
                data.cliente()
        );

        if (novoEquipamento != null) {
            equipamento.setId(novoEquipamento.getId());
        }

        return equipamento;
    }
}

package com.marcoswolf.repairflow.controller.mappers;

import com.marcoswolf.repairflow.controller.dto.TipoEquipamentoRequestDTO;
import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TipoEquipamentoRequestMapper {
    public TipoEquipamento toEntity(TipoEquipamentoRequestDTO dto) {
        TipoEquipamento tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setNome(dto.nome());

        return tipoEquipamento;
    }
}

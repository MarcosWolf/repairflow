package com.marcoswolf.repairflow.ui.mappers;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import org.springframework.stereotype.Component;

@Component
public class TipoEquipamentoFormMapper {
    public TipoEquipamento toEntity(String nome) {
        TipoEquipamento tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setNome(nome);

        return tipoEquipamento;
    }
}

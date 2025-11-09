package com.marcoswolf.crm.reparos.ui.mappers;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import org.springframework.stereotype.Component;

@Component
public class TipoEquipamentoFormMapper {
    public TipoEquipamento toEntity(String nome) {
        TipoEquipamento tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setNome(nome);

        return tipoEquipamento;
    }
}

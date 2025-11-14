package com.marcoswolf.crm.reparos.business.tipoEquipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;

import java.util.List;

public interface TipoEquipamentoConsultaService {
    List<TipoEquipamento> listarTodos();
}

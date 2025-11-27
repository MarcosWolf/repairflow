package com.marcoswolf.repairflow.business.tipoEquipamento;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;

import java.util.List;

public interface TipoEquipamentoConsultaService {
    List<TipoEquipamento> listarTodos();
}

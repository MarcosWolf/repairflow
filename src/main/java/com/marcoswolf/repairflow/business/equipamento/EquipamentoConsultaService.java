package com.marcoswolf.repairflow.business.equipamento;

import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;

import java.util.List;

public interface EquipamentoConsultaService {
    List<Equipamento> listarTodos();
    List<Equipamento> listarPorClienteId(Long id);
}

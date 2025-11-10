package com.marcoswolf.crm.reparos.business.equipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;

import java.util.List;

public interface EquipamentoConsultaService {
    List<Equipamento> listarTodos();
    List<Equipamento> listarPorClienteId(Long id);
}

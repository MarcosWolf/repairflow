package com.marcoswolf.repairflow.business.equipamento.filtro;

import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;

import java.util.List;

public interface IEquipamentoFiltroService {
    List<Equipamento> aplicarFiltros(EquipamentoFiltro filtro);
    List<Equipamento> aplicarFiltros(List<Equipamento> equipamentos, EquipamentoFiltro filtro);
}

package com.marcoswolf.repairflow.business.tipoEquipamento.filtro;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;

import java.util.List;

public interface TipoEquipamentoFiltroServiceImpl {
    List<TipoEquipamento> aplicarFiltros(TipoEquipamentoFiltro filtro);
    List<TipoEquipamento> aplicarFiltros(List<TipoEquipamento> tiposEquipamento, TipoEquipamentoFiltro filtro);
}

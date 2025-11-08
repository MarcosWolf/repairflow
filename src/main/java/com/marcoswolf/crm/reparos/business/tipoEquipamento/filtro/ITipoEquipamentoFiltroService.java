package com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;

import java.util.List;

public interface ITipoEquipamentoFiltroService {
    List<TipoEquipamento> aplicarFiltros(TipoEquipamentoFiltro filtro);
    List<TipoEquipamento> aplicarFiltros(List<TipoEquipamento> tiposEquipamento, TipoEquipamentoFiltro filtro);
}

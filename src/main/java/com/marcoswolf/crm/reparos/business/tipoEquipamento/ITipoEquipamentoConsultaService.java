package com.marcoswolf.crm.reparos.business.tipoEquipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;

import java.util.List;

public interface ITipoEquipamentoConsultaService {
    List<TipoEquipamento> listarTodos();
    //List<TipoEquipamento> filtrarTipoEquipamentos(TipoEquipamentoFiltro tipoEquipamentoFiltro);
}

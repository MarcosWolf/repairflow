package com.marcoswolf.crm.reparos.business.tipoEquipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;

public interface TipoEquipamentoComandoService {
    void salvar(TipoEquipamento tipoEquipamento);
    void deletar(Long id);
}

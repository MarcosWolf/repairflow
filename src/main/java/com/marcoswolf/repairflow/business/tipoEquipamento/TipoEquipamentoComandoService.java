package com.marcoswolf.repairflow.business.tipoEquipamento;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;

public interface TipoEquipamentoComandoService {
    void salvar(TipoEquipamento tipoEquipamento);
    void deletar(Long id);
}

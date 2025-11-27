package com.marcoswolf.repairflow.business.equipamento;

import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;

public interface EquipamentoComandoService {
    void salvar(Equipamento equipamento);
    void deletar(Long id);
}

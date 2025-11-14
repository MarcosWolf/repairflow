package com.marcoswolf.crm.reparos.business.equipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;

public interface EquipamentoComandoService {
    void salvar(Equipamento equipamento);
    void deletar(Long id);
}

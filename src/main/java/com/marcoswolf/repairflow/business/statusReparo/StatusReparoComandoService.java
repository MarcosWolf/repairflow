package com.marcoswolf.repairflow.business.statusReparo;

import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;

public interface StatusReparoComandoService {
    void salvar(StatusReparo statusReparo);
    void deletar(Long id);
}

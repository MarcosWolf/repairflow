package com.marcoswolf.repairflow.business.reparo;

import com.marcoswolf.repairflow.infrastructure.entities.Reparo;

public interface ReparoComandoService {
    void salvar(Reparo reparo);
    void deletar(Long id);
}

package com.marcoswolf.repairflow.business.reparo;

import com.marcoswolf.repairflow.infrastructure.entities.Reparo;

import java.util.List;

public interface ReparoConsultaService {
    List<Reparo> listarTodos();
}

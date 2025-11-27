package com.marcoswolf.repairflow.business.statusReparo;

import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;

import java.util.List;

public interface StatusReparoConsultaService {
    List<StatusReparo> listarTodos();
}

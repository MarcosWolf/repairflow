package com.marcoswolf.repairflow.business.cliente.filtro.strategy;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.infrastructure.repositories.ReparoRepository;

import java.time.LocalDate;

public class FiltroRecentes implements ClienteFiltroStrategy {
    private final ReparoRepository reparoRepository;

    public FiltroRecentes(ReparoRepository reparoRepository) {
        this.reparoRepository = reparoRepository;
    }

    @Override
    public boolean aplicar(Cliente cliente) {
        return reparoRepository.existsByEquipamento_Cliente_IdAndDataEntradaAfter(
                cliente.getId(), LocalDate.now().minusDays(30));
    }
}

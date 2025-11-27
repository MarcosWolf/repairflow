package com.marcoswolf.repairflow.business.cliente.filtro.strategy;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.infrastructure.repositories.ReparoRepository;

public class FiltroPendentes implements ClienteFiltroStrategy {
    private final ReparoRepository reparoRepository;

    public FiltroPendentes(ReparoRepository reparoRepository) {
        this.reparoRepository = reparoRepository;
    }

    @Override
    public boolean aplicar(Cliente cliente) {
        return reparoRepository.existsByEquipamento_Cliente_IdAndPagamento_DataPagamentoIsNull(cliente.getId());
    }
}

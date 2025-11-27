package com.marcoswolf.repairflow.ui.handler.cliente.validator;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.repairflow.infrastructure.repositories.ReparoRepository;
import com.marcoswolf.repairflow.ui.handler.cliente.dto.ClienteFormData;
import org.springframework.stereotype.Component;

@Component
public class ClienteExcluirValidator implements ClienteValidator {
    private final ReparoRepository reparoRepository;
    private final EquipamentoRepository equipamentoRepository;

    public ClienteExcluirValidator(ReparoRepository reparoRepository, EquipamentoRepository equipamentoRepository) {
        this.reparoRepository = reparoRepository;
        this.equipamentoRepository = equipamentoRepository;
    }

    @Override
    public void validar(ClienteFormData data, Cliente cliente) {
        if (reparoRepository.existsByEquipamento_Cliente_Id(cliente.getId())) {
            throw new IllegalArgumentException(
                    "Não é possível excluir o cliente: existe reparo associado."
            );
        }

        if (equipamentoRepository.existsByClienteId(cliente.getId())) {
            throw new IllegalArgumentException(
                    "Não é possível excluir o cliente: existe equipamento associado."
            );
        }
    }
}

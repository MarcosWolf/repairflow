package com.marcoswolf.repairflow.controller.mappers;

import com.marcoswolf.repairflow.controller.dto.EquipamentoRequestDTO;
import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipamentoRequestMapper {

    public Equipamento toEntity(EquipamentoRequestDTO dto) {
        Equipamento equipamento = new Equipamento();
        equipamento.setMarca(dto.marca());
        equipamento.setModelo(dto.modelo());
        equipamento.setNumeroSerie(dto.numeroSerie());

        TipoEquipamento tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setId(dto.tipoEquipamentoId());
        equipamento.setTipoEquipamento(tipoEquipamento);

        Cliente cliente = new Cliente();
        cliente.setId(dto.clienteId());
        equipamento.setCliente(cliente);

        return equipamento;
    }
}

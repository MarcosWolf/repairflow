package com.marcoswolf.crm.reparos.controller.mappers;

import com.marcoswolf.crm.reparos.controller.dto.EquipamentoRequestDTO;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
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

        var tipoEquipamentoDTO = dto.tipoEquipamentoId();
        TipoEquipamento tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setId(dto.tipoEquipamentoId());
        equipamento.setTipoEquipamento(tipoEquipamento);

        var clienteDTO = dto.clienteId();
        Cliente cliente = new Cliente();
        cliente.setId(dto.clienteId());
        equipamento.setCliente(cliente);

        return equipamento;
    }
}

package com.marcoswolf.repairflow.ui.mappers;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import org.springframework.stereotype.Component;

@Component
public class EquipamentoFormMapper {
    public Equipamento toEntity(TipoEquipamento tipoEquipamento, String marca,
                                String modelo, String numeroSerie,
                                Cliente cliente) {

        Equipamento equipamento = new Equipamento();
        equipamento.setTipoEquipamento(tipoEquipamento);
        equipamento.setMarca(marca);
        equipamento.setModelo(modelo);
        equipamento.setNumeroSerie(numeroSerie);
        equipamento.setCliente(cliente);

        return equipamento;
    }
}

package com.marcoswolf.crm.reparos.ui.handler.reparo.mapper;

import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import com.marcoswolf.crm.reparos.ui.handler.reparo.dto.ReparoFormData;
import com.marcoswolf.crm.reparos.ui.mappers.ReparoFormMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReparoFormToEntityMapper {
    private final ReparoFormMapper delegate;

    public Reparo map(ReparoFormData data, Reparo novoReparo) {
        Reparo reparo = delegate.toEntity(
                data.equipamento(),
                data.dataEntrada(),
                data.dataSaida(),
                data.descricaoProblema(),
                data.servicoExecutado(),
                data.status(),
                data.valorServico(),
                data.desconto(),
                data.dataPagamento(),
                data.pecas()
        );

         if (novoReparo != null) {
             reparo.setId(novoReparo.getId());;
         }

         return reparo;
    }
}

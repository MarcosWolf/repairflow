package com.marcoswolf.repairflow.ui.handler.reparo.mapper;

import static com.marcoswolf.repairflow.ui.utils.StringUtils.*;

import com.marcoswolf.repairflow.ui.handler.reparo.dto.ReparoFormData;
import com.marcoswolf.repairflow.ui.handler.shared.IFormNormalizer;
import org.springframework.stereotype.Component;

@Component
public class ReparoFormNormalizer implements IFormNormalizer<ReparoFormData> {
    @Override
    public ReparoFormData normalize(ReparoFormData data) {
        if (data == null) return null;

        return new ReparoFormData(
                data.equipamento(),
                data.dataEntrada(),
                data.dataSaida(),
                trimOrNull(data.descricaoProblema()),
                trimOrNull(data.servicoExecutado()),
                data.status(),
                data.valorServico(),
                data.desconto(),
                data.dataPagamento(),
                data.pecas()
        );
    }
}

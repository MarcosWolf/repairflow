package com.marcoswolf.repairflow.ui.handler.equipamento.mapper;

import static com.marcoswolf.repairflow.ui.utils.StringUtils.*;

import com.marcoswolf.repairflow.ui.handler.equipamento.dto.EquipamentoFormData;
import com.marcoswolf.repairflow.ui.handler.shared.IFormNormalizer;
import org.springframework.stereotype.Component;

@Component
public class EquipamentoFormNormalizer implements IFormNormalizer<EquipamentoFormData> {
    @Override
    public EquipamentoFormData normalize(EquipamentoFormData data) {
        if (data == null) return null;

        return new EquipamentoFormData(
                data.tipoEquipamento(),
                trimOrNull(data.marca()),
                trimOrNull(data.modelo()),
                trimOrNull(data.numeroSerie()),
                data.cliente()
        );
    }
}

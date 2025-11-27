package com.marcoswolf.repairflow.ui.handler.tipoEquipamento.mapper;

import static com.marcoswolf.repairflow.ui.utils.StringUtils.*;

import com.marcoswolf.repairflow.ui.handler.shared.IFormNormalizer;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;
import org.springframework.stereotype.Component;

@Component
public class TipoEquipamentoFormNormalizer implements IFormNormalizer<TipoEquipamentoFormData> {

    @Override
    public TipoEquipamentoFormData normalize(TipoEquipamentoFormData data) {
        if (data == null) return null;

        return new TipoEquipamentoFormData(
                trimOrNull(data.nome())
        );
    }
}

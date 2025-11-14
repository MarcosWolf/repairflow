package com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.mapper;

import static com.marcoswolf.crm.reparos.ui.utils.StringUtils.*;

import com.marcoswolf.crm.reparos.ui.handler.shared.IFormNormalizer;
import com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;
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

package com.marcoswolf.repairflow.ui.handler.statusReparo.mapper;

import com.marcoswolf.repairflow.ui.handler.shared.IFormNormalizer;
import com.marcoswolf.repairflow.ui.handler.statusReparo.dto.StatusReparoFormData;
import org.springframework.stereotype.Component;

@Component
public class StatusReparoFormNormalizer implements IFormNormalizer<StatusReparoFormData> {

    @Override
    public StatusReparoFormData normalize(StatusReparoFormData data) {
        if (data == null) return null;

        String nome = data.nome() == null ? null : data.nome().trim();

        return new StatusReparoFormData(nome);
    }
}

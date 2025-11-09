package com.marcoswolf.crm.reparos.ui.handler.statusReparo;

import com.marcoswolf.crm.reparos.ui.handler.shared.IFormNormalizer;
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

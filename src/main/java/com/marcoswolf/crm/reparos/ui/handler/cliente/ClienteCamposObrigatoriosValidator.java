package com.marcoswolf.crm.reparos.ui.handler.cliente;

import org.springframework.stereotype.Component;

@Component
public class ClienteCamposObrigatoriosValidator implements ClienteValidator {

    @Override
    public void validar(ClienteFormData data) {
        if (data.nome().isBlank() || data.telefone().isBlank() || data.cidade().isBlank()) {
            throw new IllegalArgumentException("Preencha todos os campos obrigatórios");
        }
    }
}
package com.marcoswolf.repairflow.ui.handler.cliente.mapper;

import com.marcoswolf.repairflow.infrastructure.entities.Estado;
import com.marcoswolf.repairflow.ui.handler.cliente.dto.ClienteFormData;
import com.marcoswolf.repairflow.ui.handler.shared.IFormNormalizer;
import org.springframework.stereotype.Component;

@Component
public class ClienteFormNormalizer implements IFormNormalizer<ClienteFormData> {

    @Override
    public ClienteFormData normalize(ClienteFormData data) {
        if (data == null) return null;

        String nome = data.nome() == null ? null : data.nome().trim();
        String telefone = data.telefone() == null ? null : data.telefone().trim();
        String email = data.email() == null ? null : data.email().trim();
        String documento = data.documento() == null ? null : data.documento().trim();
        String cidade = data.cidade() == null ? null : data.cidade().trim();
        String bairro = data.bairro() == null ? null : data.bairro().trim();
        String cep = data.bairro() == null ? null : data.cep().trim();
        String logradouro = data.logradouro() == null ? null : data.logradouro().trim();
        Integer numero = data.numero();
        Estado estado = data.estadoSelecionado();

        return new ClienteFormData(nome, telefone, email, documento, cidade, bairro, cep, logradouro, numero, estado);
    }
}

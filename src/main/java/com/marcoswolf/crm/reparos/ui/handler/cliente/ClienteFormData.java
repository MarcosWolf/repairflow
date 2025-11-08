package com.marcoswolf.crm.reparos.ui.handler.cliente;

import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;

public record ClienteFormData (
    String nome,
    String telefone,
    String email,
    String cidade,
    String bairro,
    String cep,
    String logradouro,
    Integer numero,
    Estado estadoSelecionado
) {}
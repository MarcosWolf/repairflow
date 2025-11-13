package com.marcoswolf.crm.reparos.ui.handler.cliente;

import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import com.marcoswolf.crm.reparos.ui.handler.shared.IFormData;

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
) implements IFormData {}
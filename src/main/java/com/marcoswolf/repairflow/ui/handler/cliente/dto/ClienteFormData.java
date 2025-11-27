package com.marcoswolf.repairflow.ui.handler.cliente.dto;

import com.marcoswolf.repairflow.infrastructure.entities.Estado;
import com.marcoswolf.repairflow.ui.handler.shared.IFormData;

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
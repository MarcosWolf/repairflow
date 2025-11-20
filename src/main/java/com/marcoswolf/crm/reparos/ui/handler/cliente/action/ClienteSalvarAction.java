package com.marcoswolf.crm.reparos.ui.handler.cliente.action;

import com.marcoswolf.crm.reparos.business.cliente.ClienteComandoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.handler.cliente.dto.ClienteFormData;
import com.marcoswolf.crm.reparos.ui.handler.cliente.mapper.ClienteFormNormalizer;
import com.marcoswolf.crm.reparos.ui.handler.cliente.mapper.ClienteFormToEntityMapper;
import com.marcoswolf.crm.reparos.ui.handler.cliente.validator.ClienteValidator;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ClienteSalvarAction implements ClienteAction {
    private final ClienteFormNormalizer normalizer;
    private final ClienteComandoService clienteComandoService;
    private final ClienteFormToEntityMapper mapper;
    private final ClienteValidator validator;
    private final AlertService alertService;

    public ClienteSalvarAction(
        ClienteFormNormalizer normalizer,
        ClienteComandoService clienteComandoService,
        ClienteFormToEntityMapper mapper,
        @Qualifier("clienteSalvarValidator") ClienteValidator validator,
        AlertService alertService
    ) {
        this.normalizer = normalizer;
        this.clienteComandoService = clienteComandoService;
        this.mapper = mapper;
        this.validator = validator;
        this.alertService = alertService;
    }

    @Override
    public boolean execute(Cliente novoCliente, ClienteFormData data) {
        try {
            ClienteFormData normalized = normalizer.normalize(data);
            validator.validar(normalized, novoCliente);

            Cliente cliente = mapper.map(normalized, novoCliente);
            clienteComandoService.salvar(cliente);

            //alertService.info("Sucesso", "Cliente salvo com sucesso!");
            return true;
        } catch (IllegalArgumentException e) {
            alertService.warn("Campos obrigatórios", e.getMessage());
            return false;
        } catch (Exception e) {
            alertService.error("Erro ao salvar", e.getMessage());
            return false;
        }
    }
}
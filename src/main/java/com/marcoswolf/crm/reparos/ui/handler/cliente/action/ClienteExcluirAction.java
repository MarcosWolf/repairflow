package com.marcoswolf.crm.reparos.ui.handler.cliente.action;

import com.marcoswolf.crm.reparos.business.cliente.ClienteComandoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.handler.cliente.dto.ClienteFormData;
import com.marcoswolf.crm.reparos.ui.handler.cliente.validator.ClienteValidator;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ClienteExcluirAction implements ClienteAction {
    private final ClienteComandoService clienteComandoService;
    private final ClienteValidator validator;
    private final AlertService alertService;

    public ClienteExcluirAction(
            ClienteComandoService clienteComandoService,
            @Qualifier("clienteExcluirValidator") ClienteValidator validator,
            AlertService alertService
    ) {
        this.clienteComandoService = clienteComandoService;
        this.validator = validator;
        this.alertService = alertService;
    }

    @Override
    public boolean execute(Cliente cliente, ClienteFormData data) {
        if (cliente == null) return false;

        boolean confirmar = alertService.confirm(
                "Confirmar exclusão",
                "Deseja realmente excluir este cliente?"
        );
        if (!confirmar) return false;

        try {
            validator.validar(data, cliente);
            clienteComandoService.deletar(cliente.getId());
            alertService.info("Sucesso", "Cliente removido com sucesso!");
            return true;
        } catch (Exception e) {
            alertService.error("Erro ao excluir", e.getMessage());
            return false;
        }
    }
}
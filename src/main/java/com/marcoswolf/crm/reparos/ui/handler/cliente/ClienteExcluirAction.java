package com.marcoswolf.crm.reparos.ui.handler.cliente;

import com.marcoswolf.crm.reparos.business.cliente.IClienteComandoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteExcluirAction implements ClienteAction {
    private final IClienteComandoService clienteComandoService;
    private final AlertService alertService;

    @Override
    public boolean execute(Cliente cliente, ClienteFormData data) {
        if (cliente == null) return false;

        boolean confirmar = alertService.confirm(
                "Confirmar exclusão",
                "Deseja realmente excluir este cliente?"
        );
        if (!confirmar) return false;

        try {
            clienteComandoService.deletarCliente(cliente.getId());
            alertService.info("Sucesso", "Cliente removido com sucesso!");
            return true;
        } catch (Exception e) {
            alertService.error("Erro ao excluir", e.getMessage());
            return false;
        }
    }
}
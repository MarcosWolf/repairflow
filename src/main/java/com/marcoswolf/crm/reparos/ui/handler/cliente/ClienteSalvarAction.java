package com.marcoswolf.crm.reparos.ui.handler.cliente;

import com.marcoswolf.crm.reparos.business.cliente.IClienteComandoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteSalvarAction implements ClienteAction {

    private final IClienteComandoService clienteComandoService;
    private final ClienteFormToEntityMapper mapper;
    private final ClienteValidator validator;
    private final AlertService alertService;

    @Override
    public boolean execute(Cliente clienteEditando, ClienteFormData data) {
        try {
            validator.validar(data);
            Cliente cliente = mapper.map(data, clienteEditando);
            clienteComandoService.salvarCliente(cliente);
            alertService.info("Sucesso", "Cliente salvo com sucesso!");
            return true;
        } catch (Exception e) {
            alertService.error("Erro ao salvar", e.getMessage());
            return false;
        }
    }
}
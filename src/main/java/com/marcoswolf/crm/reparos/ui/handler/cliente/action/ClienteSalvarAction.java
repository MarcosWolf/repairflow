package com.marcoswolf.crm.reparos.ui.handler.cliente;

import com.marcoswolf.crm.reparos.business.cliente.IClienteComandoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.handler.cliente.action.ClienteAction;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteSalvarAction implements ClienteAction {
    private final ClienteFormNormalizer normalizer;
    private final IClienteComandoService clienteComandoService;
    private final ClienteFormToEntityMapper mapper;
    private final ClienteValidator validator;
    private final AlertService alertService;

    @Override
    public boolean execute(Cliente novoCliente, ClienteFormData data) {
        try {
            ClienteFormData normalized = normalizer.normalize(data);
            validator.validar(normalized, novoCliente);

            Cliente cliente = mapper.map(normalized, novoCliente);
            clienteComandoService.salvarCliente(cliente);

            alertService.info("Sucesso", "Cliente salvo com sucesso!");
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
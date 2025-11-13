package com.marcoswolf.crm.reparos.ui.handler.cliente;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.controller.MainViewController;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteAbrirFormAction {
    private final ViewNavigator navigator;
    private final MainViewController mainViewController;

    public void abrirFormulario(Cliente cliente) {
        navigator.openView(
                "/fxml/cliente/cliente-form.fxml",
                mainViewController.getContentArea(),
                cliente
        );
    }
}

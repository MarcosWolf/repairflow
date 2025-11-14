package com.marcoswolf.crm.reparos.ui.handler.cliente.action;

import com.marcoswolf.crm.reparos.business.cliente.ClienteConsultaService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteLimparFiltrosAction {
    private final ClienteConsultaService clienteConsultaService;
    private final AlertService alertService;

    public void executar(CheckBox chkPendentes,
                         CheckBox chkRecentes,
                         CheckBox chkInativos,
                         TextField txtBuscar,
                         TableView<Cliente> tabelaClientes) {
        try {
            chkPendentes.setSelected(false);
            chkRecentes.setSelected(false);
            chkInativos.setSelected(false);
            txtBuscar.clear();

            var clientes = clienteConsultaService.listarTodos();
            tabelaClientes.setItems(FXCollections.observableList(clientes));
        } catch (Exception e) {
            alertService.error("Erro", "Falha ao limpar filtros: " + e.getMessage());
        }
    }
}

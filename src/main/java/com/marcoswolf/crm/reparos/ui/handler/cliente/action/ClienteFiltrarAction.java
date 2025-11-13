package com.marcoswolf.crm.reparos.ui.handler.cliente;

import com.marcoswolf.crm.reparos.business.cliente.filtro.ClienteFiltro;
import com.marcoswolf.crm.reparos.business.cliente.IClienteConsultaService;
import com.marcoswolf.crm.reparos.business.cliente.filtro.IClienteFiltroService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClienteFiltrarAction {
    private final IClienteFiltroService clienteFiltroService;
    private final AlertService alertService;

    public List<Cliente> executar(ClienteFiltroDTO filtroDTO) {
        ClienteFiltro filtro = toFiltro(filtroDTO);

        try {
            return clienteFiltroService.aplicarFiltros(filtro);
        } catch (Exception e) {
            return List.of();
        }
    }

    private ClienteFiltro toFiltro(ClienteFiltroDTO dto) {
        ClienteFiltro filtro = new ClienteFiltro();
        filtro.setNome(dto.nome());
        filtro.setPendentes(dto.pendentes());
        filtro.setInativos(dto.inativos());
        filtro.setRecentes(dto.recentes());
        filtro.setDataDesde(dto.dataDesde());
        return filtro;
    }

}

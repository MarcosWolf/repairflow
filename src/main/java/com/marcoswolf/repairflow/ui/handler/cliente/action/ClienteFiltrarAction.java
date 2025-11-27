package com.marcoswolf.repairflow.ui.handler.cliente.action;

import com.marcoswolf.repairflow.business.cliente.filtro.ClienteFiltro;
import com.marcoswolf.repairflow.business.cliente.filtro.ClienteFiltroServiceImpl;
import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.ui.handler.cliente.dto.ClienteFiltroDTO;
import com.marcoswolf.repairflow.ui.utils.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClienteFiltrarAction {
    private final ClienteFiltroServiceImpl clienteFiltroService;
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

package com.marcoswolf.crm.reparos.ui.handler.statusReparo;

import com.marcoswolf.crm.reparos.business.statusReparo.StatusReparoComandoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.StatusReparo;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusReparoExcluirAction implements StatusReparoAction {
    private final StatusReparoComandoService statusReparoComandoService;
    private final AlertService alertService;

    @Override
    public boolean execute(StatusReparo statusReparo, StatusReparoFormData data) {
        if (statusReparo == null) return false;

        boolean confirmar = alertService.confirm(
                "Confirmar exclusão",
                "Deseja realmente excluir este status de reparo?"
        );
        if (!confirmar) return false;

        try {
            statusReparoComandoService.deletar(statusReparo.getId());
            alertService.info("Sucesso", "Status de reparo removido com sucesso!");
            return true;
        } catch (Exception e) {
            alertService.error("Erro ao excluir", e.getMessage());
            return false;
        }
    }
}

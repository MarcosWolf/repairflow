package com.marcoswolf.repairflow.ui.handler.reparo.action;

import com.marcoswolf.repairflow.business.reparo.ReparoComandoService;
import com.marcoswolf.repairflow.infrastructure.entities.Reparo;
import com.marcoswolf.repairflow.ui.handler.reparo.dto.ReparoFormData;
import com.marcoswolf.repairflow.ui.handler.reparo.validator.ReparoExcluirValidator;
import com.marcoswolf.repairflow.ui.utils.AlertService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReparoExcluirAction implements ReparoAction {
    private final ReparoComandoService reparoComandoService;
    private final ReparoExcluirValidator validator;
    private final AlertService alertService;

    public ReparoExcluirAction(
            ReparoComandoService reparoComandoService,
            @Qualifier("reparoExcluirValidator") ReparoExcluirValidator validator,
            AlertService alertService
    ) {
        this.reparoComandoService = reparoComandoService;
        this.validator = validator;
        this.alertService = alertService;
    }

    @Override
    public boolean execute(Reparo reparo, ReparoFormData data) {
        if (reparo == null) return false;

        boolean confirmar = alertService.confirm(
                "Confirmar exclusão",
                "Deseja realmente excluir este reparo?"
        );
        if (!confirmar) return false;

        try {
            validator.validar(data, reparo);
            reparoComandoService.deletar(reparo.getId());
            alertService.info("Sucesso", "Reparo removido com sucesso!");
            return true;
        } catch (Exception e) {
            alertService.error("Erro ao excluir", e.getMessage());
            return false;
        }
    }
}

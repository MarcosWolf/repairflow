package com.marcoswolf.repairflow.ui.handler.tipoEquipamento.action;

import com.marcoswolf.repairflow.business.tipoEquipamento.TipoEquipamentoComandoService;
import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.validator.TipoEquipamentoValidator;
import com.marcoswolf.repairflow.ui.utils.AlertService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TipoEquipamentoExcluirAction implements TipoEquipamentoAction {
    private final TipoEquipamentoComandoService tipoEquipamentoComandoService;
    private final TipoEquipamentoValidator validator;
    private final AlertService alertService;

    public TipoEquipamentoExcluirAction(
            TipoEquipamentoComandoService tipoEquipamentoComandoService,
            @Qualifier("tipoEquipamentoExcluirValidator") TipoEquipamentoValidator validator,
            AlertService alertService
    ) {
        this.tipoEquipamentoComandoService = tipoEquipamentoComandoService;
        this.validator = validator;
        this.alertService = alertService;
    }

    @Override
    public boolean execute(TipoEquipamento tipoEquipamento, TipoEquipamentoFormData data) {
        if (tipoEquipamento == null) return false;

        boolean confirmar = alertService.confirm(
                "Confirmar exclusão",
                "Deseja realmente excluir este tipo de equipamento?"
        );
        if (!confirmar) return false;

        try {
            validator.validar(data, tipoEquipamento);
            tipoEquipamentoComandoService.deletar(tipoEquipamento.getId());
            alertService.info("Sucesso", "Tipo de equipamento removido com sucesso!");
            return true;
        } catch (Exception e) {
            alertService.error("Erro ao excluir", e.getMessage());
            return false;
        }
    }
}
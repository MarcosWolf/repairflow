package com.marcoswolf.repairflow.ui.handler.equipamento.action;

import com.marcoswolf.repairflow.business.equipamento.EquipamentoComandoService;
import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.ui.handler.equipamento.dto.EquipamentoFormData;
import com.marcoswolf.repairflow.ui.handler.equipamento.validator.EquipamentoValidator;
import com.marcoswolf.repairflow.ui.utils.AlertService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EquipamentoExcluirAction implements EquipamentoAction {
    private final EquipamentoComandoService equipamentoComandoService;
    private final EquipamentoValidator validator;
    private final AlertService alertService;

    public EquipamentoExcluirAction(
            EquipamentoComandoService equipamentoComandoService,
            @Qualifier("equipamentoExcluirValidator") EquipamentoValidator validator,
            AlertService alertService
    ) {
        this.equipamentoComandoService = equipamentoComandoService;
        this.validator = validator;
        this.alertService = alertService;
    }

    @Override
    public boolean execute(Equipamento equipamento, EquipamentoFormData data) {
        if (equipamento == null) return false;

        boolean confirmar = alertService.confirm(
                "Confirmar exclusão",
                "Deseja realmente excluir este tipo de equipamento?"
        );
        if (!confirmar) return false;

        try {
            validator.validar(data, equipamento);
            equipamentoComandoService.deletar(equipamento.getId());
            alertService.info("Sucesso", "Equipamento removido com sucesso!");
            return true;
        } catch (Exception e) {
            alertService.error("Erro ao excluir", e.getMessage());
            return false;
        }
    }
}

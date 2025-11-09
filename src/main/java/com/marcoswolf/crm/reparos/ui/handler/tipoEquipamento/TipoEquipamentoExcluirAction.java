package com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento;

import com.marcoswolf.crm.reparos.business.tipoEquipamento.ITipoEquipamentoComandoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TipoEquipamentoExcluirAction implements TipoEquipamentoAction {
    private final ITipoEquipamentoComandoService tipoEquipamentoComandoService;
    private final AlertService alertService;

    @Override
    public boolean execute(TipoEquipamento tipoEquipamento, TipoEquipamentoFormData data) {
        if (tipoEquipamento == null) return false;

        boolean confirmar = alertService.confirm(
                "Confirmar exclusão",
                "Deseja realmente excluir este tipo de equipamento?"
        );
        if (!confirmar) return false;

        try {
            tipoEquipamentoComandoService.deletarTipoEquipamento(tipoEquipamento.getId());
            alertService.info("Sucesso", "Tipo de equipamento removido com sucesso!");
            return true;
        } catch (Exception e) {
            alertService.error("Erro ao excluir", e.getMessage());
            return false;
        }
    }
}
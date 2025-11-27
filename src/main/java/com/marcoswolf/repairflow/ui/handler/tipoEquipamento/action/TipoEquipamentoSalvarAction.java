package com.marcoswolf.repairflow.ui.handler.tipoEquipamento.action;

import com.marcoswolf.repairflow.business.tipoEquipamento.TipoEquipamentoComandoService;
import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.mapper.TipoEquipamentoFormNormalizer;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.mapper.TipoEquipamentoFormToEntityMapper;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.validator.TipoEquipamentoSalvarValidator;
import com.marcoswolf.repairflow.ui.handler.tipoEquipamento.validator.TipoEquipamentoValidator;
import com.marcoswolf.repairflow.ui.utils.AlertService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TipoEquipamentoSalvarAction implements TipoEquipamentoAction {
    private final TipoEquipamentoFormNormalizer normalizer;
    private final TipoEquipamentoComandoService tipoEquipamentoComandoService;
    private final TipoEquipamentoFormToEntityMapper mapper;
    private final TipoEquipamentoValidator validator;
    private final AlertService alertService;

    public TipoEquipamentoSalvarAction(
        TipoEquipamentoFormNormalizer normalizer,
        TipoEquipamentoComandoService tipoEquipamentoComandoService,
        TipoEquipamentoFormToEntityMapper mapper,
        @Qualifier("tipoEquipamentoSalvarValidator") TipoEquipamentoSalvarValidator validator,
        AlertService alertService
    ) {
        this.normalizer = normalizer;
        this.tipoEquipamentoComandoService = tipoEquipamentoComandoService;
        this.mapper = mapper;
        this.validator = validator;
        this.alertService = alertService;
    }

    @Override
    public boolean execute(TipoEquipamento novoTipoEquipamento, TipoEquipamentoFormData data) {
        try {
            TipoEquipamentoFormData normalized = normalizer.normalize(data);
            validator.validar(normalized, novoTipoEquipamento);

            TipoEquipamento tipoEquipamento = mapper.map(normalized, novoTipoEquipamento);
            tipoEquipamentoComandoService.salvar(tipoEquipamento);

            alertService.info("Sucesso", "Tipo de equipamento salvo com sucesso!");
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

package com.marcoswolf.repairflow.ui.handler.statusReparo.action;

import com.marcoswolf.repairflow.business.statusReparo.StatusReparoComandoService;
import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import com.marcoswolf.repairflow.ui.handler.statusReparo.dto.StatusReparoFormData;
import com.marcoswolf.repairflow.ui.handler.statusReparo.mapper.StatusReparoFormNormalizer;
import com.marcoswolf.repairflow.ui.handler.statusReparo.mapper.StatusReparoFormToEntityMapper;
import com.marcoswolf.repairflow.ui.handler.statusReparo.validator.StatusReparoValidator;
import com.marcoswolf.repairflow.ui.utils.AlertService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class StatusReparoSalvarAction implements StatusReparoAction {
    private final StatusReparoFormNormalizer normalizer;
    private final StatusReparoComandoService statusReparoComandoServicer;
    private final StatusReparoFormToEntityMapper mapper;
    private final StatusReparoValidator validator;
    private final AlertService alertService;

    public StatusReparoSalvarAction(
        StatusReparoFormNormalizer normalizer,
        StatusReparoComandoService statusReparoComandoService,
        StatusReparoFormToEntityMapper mapper,
        @Qualifier("statusReparoSalvarValidator") StatusReparoValidator validator,
        AlertService alertService
    ) {
        this.normalizer = normalizer;
        this.statusReparoComandoServicer = statusReparoComandoService;
        this.mapper = mapper;
        this.validator = validator;
        this.alertService = alertService;
    }

    @Override
    public boolean execute(StatusReparo novoStatusReparo, StatusReparoFormData data) {
        try {
            StatusReparoFormData normalized = normalizer.normalize(data);
            validator.validar(normalized, novoStatusReparo);

            StatusReparo statusReparo = mapper.map(normalized, novoStatusReparo);
            statusReparoComandoServicer.salvar(statusReparo);

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

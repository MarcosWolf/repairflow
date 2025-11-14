package com.marcoswolf.crm.reparos.ui.handler.reparo.action;

import com.marcoswolf.crm.reparos.business.reparo.ReparoComandoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import com.marcoswolf.crm.reparos.ui.handler.reparo.dto.ReparoFormData;
import com.marcoswolf.crm.reparos.ui.handler.reparo.mapper.ReparoFormNormalizer;
import com.marcoswolf.crm.reparos.ui.handler.reparo.mapper.ReparoFormToEntityMapper;
import com.marcoswolf.crm.reparos.ui.handler.reparo.validator.ReparoValidator;
import com.marcoswolf.crm.reparos.ui.utils.AlertService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReparoSalvarAction implements ReparoAction {
    private final ReparoFormNormalizer normalizer;
    private final ReparoComandoService reparoComandoService;;
    private final ReparoFormToEntityMapper mapper;
    private final ReparoValidator validator;
    private final AlertService alertService;

    public ReparoSalvarAction(
        ReparoFormNormalizer normalizer,
        ReparoComandoService reparoComandoService,
        ReparoFormToEntityMapper mapper,
        @Qualifier("reparoSalvarValidator") ReparoValidator validator,
        AlertService alertService
    ) {
        this.normalizer = normalizer;
        this.reparoComandoService = reparoComandoService;
        this.mapper = mapper;
        this.validator = validator;
        this.alertService = alertService;
    }

    @Override
    public boolean execute(Reparo novoReparo, ReparoFormData data) {
        try {
            ReparoFormData normalized = normalizer.normalize(data);
            validator.validar(normalized, novoReparo);

            Reparo reparo = mapper.map(normalized, novoReparo);
            reparoComandoService.salvar(reparo);

            alertService.info("Sucesso", "Reparo salvo com sucesso!");
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
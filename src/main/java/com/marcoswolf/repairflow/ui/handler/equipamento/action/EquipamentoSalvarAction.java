package com.marcoswolf.repairflow.ui.handler.equipamento.action;

import com.marcoswolf.repairflow.business.equipamento.EquipamentoComandoService;
import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.ui.handler.equipamento.dto.EquipamentoFormData;
import com.marcoswolf.repairflow.ui.handler.equipamento.mapper.EquipamentoFormNormalizer;
import com.marcoswolf.repairflow.ui.handler.equipamento.mapper.EquipamentoFormToEntityMapper;
import com.marcoswolf.repairflow.ui.handler.equipamento.validator.EquipamentoValidator;
import com.marcoswolf.repairflow.ui.utils.AlertService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EquipamentoSalvarAction implements EquipamentoAction {
    private final EquipamentoFormNormalizer normalizer;
    private final EquipamentoComandoService equipamentoComandoService;
    private final EquipamentoFormToEntityMapper mapper;
    private final EquipamentoValidator validator;
    private final AlertService alertService;

    public EquipamentoSalvarAction(
            EquipamentoFormNormalizer normalizer,
            EquipamentoComandoService equipamentoComandoService,
            EquipamentoFormToEntityMapper mapper,
            @Qualifier("equipamentoSalvarValidator") EquipamentoValidator validator,
            AlertService alertService
    ) {
        this.normalizer = normalizer;
        this.equipamentoComandoService = equipamentoComandoService;
        this.mapper = mapper;
        this.validator = validator;
        this.alertService = alertService;
    }

    @Override
    public boolean execute(Equipamento novoEquipamento, EquipamentoFormData data) {
        try {
            EquipamentoFormData normalized = normalizer.normalize(data);
            validator.validar(normalized, novoEquipamento);

            Equipamento equipamento = mapper.map(normalized, novoEquipamento);
            equipamentoComandoService.salvar(equipamento);

            alertService.info("Sucesso", "Equipamento salvo com sucesso!");
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

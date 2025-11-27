package com.marcoswolf.repairflow.ui.handler.statusReparo.mapper;

import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import com.marcoswolf.repairflow.ui.handler.statusReparo.dto.StatusReparoFormData;
import com.marcoswolf.repairflow.ui.mappers.StatusReparoFormMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusReparoFormToEntityMapper {
    private final StatusReparoFormMapper delegate;

    public StatusReparo map(StatusReparoFormData data, StatusReparo novoStatusReparo) {
        StatusReparo statusReparo = delegate.toEntity(
                data.nome()
        );

        if (novoStatusReparo != null) {
            statusReparo.setId(novoStatusReparo.getId());
        }
        return statusReparo;
    }
}

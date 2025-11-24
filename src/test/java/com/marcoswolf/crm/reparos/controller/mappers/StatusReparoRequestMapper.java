package com.marcoswolf.crm.reparos.controller.mappers;

import com.marcoswolf.crm.reparos.controller.dto.StatusReparoRequestDTO;
import com.marcoswolf.crm.reparos.infrastructure.entities.StatusReparo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusReparoRequestMapper {
    public StatusReparo toEntity(StatusReparoRequestDTO dto) {
        StatusReparo statusReparo = new StatusReparo();
        statusReparo.setNome(dto.nome());

        return statusReparo;
    }
}

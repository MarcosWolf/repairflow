package com.marcoswolf.repairflow.ui.mappers;

import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import org.springframework.stereotype.Component;

@Component
public class StatusReparoFormMapper {
    public StatusReparo toEntity(String nome) {
        StatusReparo statusReparo = new StatusReparo();
        statusReparo.setNome(nome);

        return statusReparo;
    }
}

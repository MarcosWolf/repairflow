package com.marcoswolf.repairflow.business.estado;

import com.marcoswolf.repairflow.infrastructure.entities.Estado;
import com.marcoswolf.repairflow.infrastructure.repositories.EstadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService implements EstadoConsultaService {
    private final EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    @Override
    public List<Estado> listarTodos() {
        return estadoRepository.findAll();
    }
}

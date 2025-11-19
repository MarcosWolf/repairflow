package com.marcoswolf.crm.reparos.business.statusReparo;

import com.marcoswolf.crm.reparos.infrastructure.entities.StatusReparo;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.StatusReparoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusReparoService implements StatusReparoConsultaService, StatusReparoComandoService {
    private final StatusReparoRepository statusReparoRepository;
    private final ReparoRepository reparoRepository;

    public StatusReparoService(StatusReparoRepository statusReparoRepository, ReparoRepository reparoRepository) {
        this.statusReparoRepository = statusReparoRepository;
        this.reparoRepository = reparoRepository;
    }

    public List<StatusReparo> listarTodos() {
        return statusReparoRepository.findAll();
    }

    public Optional<StatusReparo> buscarPorId(Long id) {
        return statusReparoRepository.findById(id);
    }

    public Long contarReparosPorStatusReparo(Long tipoId) {
        return reparoRepository.countByTipoEquipamentoId(tipoId);
    }

    public void salvar(StatusReparo statusReparo) {
        statusReparoRepository.saveAndFlush(statusReparo);
    }

    public void deletar(Long id) {
        var statusReparo = statusReparoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status de reparo não encontrado."));

        statusReparoRepository.delete(statusReparo);
    }
}

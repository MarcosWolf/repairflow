package com.marcoswolf.crm.reparos.business.reparo;

import com.marcoswolf.crm.reparos.infrastructure.entities.Pagamento;
import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReparoService implements ReparoConsultaService, ReparoComandoService {
    private final ReparoRepository reparoRepository;

    public ReparoService(ReparoRepository reparoRepository) {
        this.reparoRepository = reparoRepository;
    }

    @Transactional
    public List<Reparo> listarTodos() {
        return reparoRepository.findAllCompletos();
    }

    @Transactional
    public void salvar(Reparo reparo) {
        reparoRepository.saveAndFlush(reparo);
    }

    @Transactional
    public Optional<Reparo> buscarPorId(Long id) {
        return reparoRepository.findById(id);
    }

    @Transactional
    public void deletar(Long id) {
        var reparo = reparoRepository.findByIdComPagamentoEPecas(id)
                .orElseThrow(() -> new RuntimeException("Reparo não encontrado."));

        reparoRepository.delete(reparo);
    }
}

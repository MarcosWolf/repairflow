package com.marcoswolf.crm.reparos.business;

import com.marcoswolf.crm.reparos.infrastructure.entities.Pagamento;
import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReparoService {
    private final ReparoRepository reparoRepository;

    public ReparoService(ReparoRepository reparoRepository) {
        this.reparoRepository = reparoRepository;
    }

    // Create
    public void salvarReparo(Reparo reparo) {
        reparoRepository.saveAndFlush(reparo);
    }

    // Read
    public List<Reparo> buscarPorStatus(String status) {
        var reparos = reparoRepository.findByStatus_NomeContainingIgnoreCase(status);

        if (reparos.isEmpty()) {
            throw new RuntimeException("Reparo não encontrado.");
        }

        return reparos;
    }

    // Update
    public Reparo atualizarReparo(Long id, Reparo novoReparo) {
        var reparo = reparoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reparo não encontrado."));

        reparo.setDataEntrada(novoReparo.getDataEntrada());
        reparo.setDataSaida(novoReparo.getDataSaida());
        reparo.setDescricaoProblema(novoReparo.getDescricaoProblema());
        reparo.setServicoExecutado(novoReparo.getServicoExecutado());
        reparo.setEquipamento(novoReparo.getEquipamento());
        reparo.setStatus(novoReparo.getStatus());

        var pagamento = reparo.getPagamento();
        var novoPagamento = novoReparo.getPagamento();

        if (pagamento == null) {
            pagamento = new Pagamento();
            pagamento.setReparo(reparo);
            reparo.setPagamento(pagamento);
        }

        if (novoPagamento != null) {
            pagamento.setValorServico(novoPagamento.getValorServico());
            pagamento.setDesconto(novoPagamento.getDesconto());
            pagamento.setDataPagamento(novoPagamento.getDataPagamento());
            pagamento.setPago(novoPagamento.getPago());

            if (pagamento.getPecas() == null) {
                pagamento.setPecas(new ArrayList<>());
            } else {
                pagamento.getPecas().clear();
            }

            if (novoPagamento.getPecas() != null) {
                pagamento.getPecas().addAll(novoPagamento.getPecas());
            }
        }

        return reparoRepository.saveAndFlush(reparo);
    }

    // Delete
    public void deletarReparo(Long id) {
        var reparo = reparoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reparo não encontrado."));

        reparoRepository.delete(reparo);
    }
}

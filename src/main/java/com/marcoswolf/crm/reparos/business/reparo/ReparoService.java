package com.marcoswolf.crm.reparos.business.reparo;

import com.marcoswolf.crm.reparos.infrastructure.entities.Pagamento;
import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReparoService implements ReparoConsultaService, ReparoComandoService {
    private final ReparoRepository reparoRepository;

    public ReparoService(ReparoRepository reparoRepository) {
        this.reparoRepository = reparoRepository;
    }

    // Create
    @Transactional
    public void salvar(Reparo reparo) {
        reparoRepository.saveAndFlush(reparo);
    }

    // Read
    @Transactional
    public List<Reparo> listarTodos() {
        return reparoRepository.findAllCompletos();
    }

    @Transactional
    public Reparo buscarPorIdComPagamentoEPecas(Long id) {
        return reparoRepository.findByIdComPagamentoEPecas(id)
                .orElseThrow(() -> new RuntimeException("Reparo não encontrado."));
    }

    // Update
    @Transactional
    public Reparo atualizar(Long id, Reparo novoReparo) {
        var reparo = reparoRepository.findByIdComPagamentoEPecas(id)
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
    @Transactional
    public void deletar(Long id) {
        var reparo = reparoRepository.findByIdComPagamentoEPecas(id)
                .orElseThrow(() -> new RuntimeException("Reparo não encontrado."));

        reparoRepository.delete(reparo);
    }
}

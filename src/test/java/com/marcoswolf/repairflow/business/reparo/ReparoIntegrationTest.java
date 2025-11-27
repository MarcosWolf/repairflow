package com.marcoswolf.repairflow.business.reparo;

import com.marcoswolf.repairflow.infrastructure.entities.*;
import com.marcoswolf.repairflow.infrastructure.entities.*;
import com.marcoswolf.repairflow.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.repairflow.infrastructure.repositories.ReparoRepository;
import com.marcoswolf.repairflow.infrastructure.repositories.StatusReparoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ReparoIntegrationTest {
    @Autowired private ReparoService reparoService;

    @Autowired private ReparoRepository reparoRepository;
    @Autowired private EquipamentoRepository equipamentoRepository;
    @Autowired private StatusReparoRepository statusReparoRepository;

    @AfterEach
    void limparBanco() {
        reparoRepository.deleteAll();
    }

    @Test
    void deveListarTodosOsReparos() {
        Reparo reparo = criarReparoPadrao();
        reparoRepository.save(reparo);

        var resultado = reparoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.stream().anyMatch(c -> c.getStatus().getNome().equals("Aguardando Pagamento")));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremReparos() {
        var resultado = reparoService.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveSalvarUmNovoReparo() {
        Reparo reparo = criarReparoPadrao();
        reparoRepository.save(reparo);

        var reparoSalvo = reparoRepository.findAll().get(0);
        assertNotNull(reparo.getId());
        assertEquals("Não sai som", reparoSalvo.getDescricaoProblema());
        assertEquals("Limpeza e troca de capacitor", reparoSalvo.getServicoExecutado());
    }

    @Test
    void deveAtualizarUmReparo() {
        Reparo reparo = criarReparoPadrao();

        reparoRepository.save(reparo);

        reparo.setDescricaoProblema("Cabo flat estourado");
        reparo.setServicoExecutado("Cabo flat reparado");

        reparoService.salvar(reparo);

        var reparoAtualizado = reparoRepository.findById(reparo.getId()).orElseThrow();

        assertNotNull(reparo.getId());
        assertEquals("Cabo flat estourado", reparoAtualizado.getDescricaoProblema());
        assertEquals("Cabo flat reparado", reparoAtualizado.getServicoExecutado());
    }

    @Test
    void deveDeletarUmReparo() {
        Reparo reparo = criarReparoPadrao();
        reparoRepository.save(reparo);

        Long reparoId = reparo.getId();

        reparoService.deletar(reparoId);

        assertFalse(reparoRepository.existsById(reparoId));
    }

    @Test
    void deveLancarExcecaoAoExcluirUmReparoInexistente() {
        var exception = assertThrows(RuntimeException.class,
                () -> reparoService.deletar(999L)
        );

        assertEquals("Reparo não encontrado.", exception.getMessage());
    }

    private Reparo criarReparo(
            LocalDate dataEntrada, LocalDate dataSaida, String descricaoProblema,
            String servicoExecutado, Equipamento equipamento, StatusReparo statusReparo,
            Pagamento pagamento
    ) {
        Reparo reparo = new Reparo();
        reparo.setDataEntrada(dataEntrada);
        reparo.setDataSaida(dataSaida);
        reparo.setDescricaoProblema(descricaoProblema);
        reparo.setServicoExecutado(servicoExecutado);
        reparo.setEquipamento(equipamento);
        reparo.setStatus(statusReparo);
        reparo.setPagamento(pagamento);

        return reparo;
    }

    private StatusReparo criarStatusReparo(
            String nome
    ) {
        StatusReparo statusReparoAux = new StatusReparo();
        statusReparoAux.setNome(nome);
        statusReparoRepository.save(statusReparoAux);

        return statusReparoAux;
    }

    private Equipamento criarEquipamento(
            String marca
    ) {
        Equipamento equipamentoAux = new Equipamento();
        equipamentoAux.setMarca(marca);
        equipamentoRepository.save(equipamentoAux);

        return equipamentoAux;
    }

    private Pagamento criarPagamento(
            LocalDate dataPagamento, BigDecimal valorServico, BigDecimal valorDesconto,
            List<PecaPagamento> pecaPagamento
    ) {
        Pagamento pagamentoAux = new Pagamento();
        pagamentoAux.setDataPagamento(dataPagamento);
        pagamentoAux.setValorServico(valorServico);
        pagamentoAux.setDesconto(valorDesconto);
        pagamentoAux.setPecas(new ArrayList<>(pecaPagamento));

        return pagamentoAux;
    }

    private PecaPagamento criarPecaPagamento(
            String nome, Integer quantidade, BigDecimal valor
    ) {
        PecaPagamento pecaPagamentoAux = new PecaPagamento();
        pecaPagamentoAux.setNome(nome);
        pecaPagamentoAux.setQuantidade(quantidade);
        pecaPagamentoAux.setValor(valor);

        return pecaPagamentoAux;
    }

    private Reparo criarReparoPadrao() {
        Equipamento equipamento = criarEquipamento("Behringer");
        StatusReparo statusReparo = criarStatusReparo("Aguardando Pagamento");
        PecaPagamento pecaPagamento = criarPecaPagamento("Capacitor", 5, new BigDecimal("25.00"));
        Pagamento pagamento = criarPagamento(null, new BigDecimal("150.00"),
                new BigDecimal("20.00"), List.of(pecaPagamento));

        return criarReparo(LocalDate.parse("2025-11-05"), LocalDate.parse("2025-11-17"),
                "Não sai som", "Limpeza e troca de capacitor",
                equipamento, statusReparo, pagamento);
    }

}

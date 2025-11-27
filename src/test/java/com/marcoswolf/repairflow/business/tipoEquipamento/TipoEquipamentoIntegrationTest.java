package com.marcoswolf.repairflow.business.tipoEquipamento;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.repairflow.infrastructure.repositories.TipoEquipamentoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TipoEquipamentoIntegrationTest {
    @Autowired private TipoEquipamentoService tipoEquipamentoService;

    @Autowired private TipoEquipamentoRepository tipoEquipamentoRepository;

    @AfterEach
    void limparBanco() {
        tipoEquipamentoRepository.deleteAll();
    }

    @Test
    void deveListarTodosOsTipoEquipamento() {
        TipoEquipamento tipo1 = criarTipoEquipamento("Mesa de Som");
        TipoEquipamento tipo2 = criarTipoEquipamento("Teclado Digital");

        tipoEquipamentoRepository.save(tipo1);
        tipoEquipamentoRepository.save(tipo2);

        var resultado = tipoEquipamentoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Mesa de Som")));
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Teclado Digital")));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremTiposEquipamento() {
        var resultado = tipoEquipamentoService.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveSalvarUmNovoTipoEquipamento() {
        TipoEquipamento tipoEquipamento = criarTipoEquipamento("Equalizador");
        tipoEquipamentoService.salvar(tipoEquipamento);

        var tipoEquipamentoSalvo = tipoEquipamentoRepository.findAll().get(0);
        assertNotNull(tipoEquipamentoSalvo.getId());
        assertEquals("Equalizador", tipoEquipamentoSalvo.getNome());
    }

    @Test
    void deveAtualizarUmTipoEquipamento() {
        TipoEquipamento tipoEquipamento = criarTipoEquipamento("Caixa de Som");
        tipoEquipamentoService.salvar(tipoEquipamento);

        tipoEquipamento.setNome("Amplificador");
        tipoEquipamentoService.salvar(tipoEquipamento);

        var tipoAtualizado = tipoEquipamentoRepository.findById(tipoEquipamento.getId()).orElseThrow();

        assertNotNull(tipoEquipamento);
        assertEquals("Amplificador", tipoAtualizado.getNome());
    }

    @Test
    void deveDeletarUmTipoEquipamento() {
        TipoEquipamento tipoEquipamento = criarTipoEquipamento("Baixo");
        tipoEquipamentoService.salvar(tipoEquipamento);

        Long tipoId = tipoEquipamento.getId();

        tipoEquipamentoService.deletar(tipoId);

        assertFalse(tipoEquipamentoRepository.existsById(tipoId));
    }

    @Test
    void deveLancarExcecaoAoExcluirUmTipoEquipamentoInexistente() {
        var exception = assertThrows(RuntimeException.class,
                () -> tipoEquipamentoService.deletar(999L)
        );

        assertEquals("Tipo de equipamento não encontrado.", exception.getMessage());
    }

    private TipoEquipamento criarTipoEquipamento(String nome) {
        TipoEquipamento tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setNome(nome);

        return tipoEquipamento;
    }
}

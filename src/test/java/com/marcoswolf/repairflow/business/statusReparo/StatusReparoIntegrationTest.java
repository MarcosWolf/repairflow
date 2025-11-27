package com.marcoswolf.repairflow.business.statusReparo;

import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import com.marcoswolf.repairflow.infrastructure.repositories.StatusReparoRepository;
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
public class StatusReparoIntegrationTest {
    @Autowired private StatusReparoService statusReparoService;

    @Autowired private StatusReparoRepository statusReparoRepository;

    @AfterEach
    void limparBanco() {
        statusReparoRepository.deleteAll();
    }

    @Test
    void deveListarTodosOsStatusReparo() {
        StatusReparo status1 = criarStatusReparo("Aguardando análise");
        StatusReparo status2 = criarStatusReparo("Em andamento");

        statusReparoRepository.save(status1);
        statusReparoRepository.save(status2);

        var resultado = statusReparoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Aguardando análise")));
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Em andamento")));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremStatusReparo() {
        var resultado = statusReparoService.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveSalvarUmStatusReparo() {
        StatusReparo status = criarStatusReparo("Concluído");
        statusReparoService.salvar(status);

        var statusSalvo = statusReparoRepository.findAll().get(0);
        assertNotNull(statusSalvo.getId());
        assertEquals("Concluído", statusSalvo.getNome());
    }

    @Test
    void deveAtualizarUmStatusReparo() {
        StatusReparo status = criarStatusReparo("Aguardando pagamento");
        statusReparoService.salvar(status);

        status.setNome("Pagamento efetuado");
        statusReparoService.salvar(status);

        var statusAtualizado = statusReparoRepository.findById(status.getId()).orElseThrow();

        assertNotNull(status);
        assertEquals("Pagamento efetuado", statusAtualizado.getNome());
    }

    @Test
    void deveDeletarUmStatusReparo() {
        StatusReparo status = criarStatusReparo("Aguardando Peça");
        statusReparoService.salvar(status);

        Long statusId = status.getId();

        statusReparoService.deletar(statusId);

        assertFalse(statusReparoRepository.existsById(statusId));
    }

    @Test
    void deveLancarExcecaoAoExcluirUmStatusReparoInexistente() {
        var exception = assertThrows(RuntimeException.class,
                () -> statusReparoService.deletar(999L)
        );

        assertEquals("Status de reparo não encontrado.", exception.getMessage());
    }

    private StatusReparo criarStatusReparo(String nome) {
        StatusReparo statusReparo = new StatusReparo();
        statusReparo.setNome(nome);

        return statusReparo;
    }
}

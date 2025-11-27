package com.marcoswolf.repairflow.business.estado;

import com.marcoswolf.repairflow.infrastructure.entities.Estado;
import com.marcoswolf.repairflow.infrastructure.repositories.EstadoRepository;
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
public class EstadoIntegrationTest {
    @Autowired private EstadoService estadoService;

    @Autowired private EstadoRepository estadoRepository;

    @AfterEach
    void limparBanco() {
        estadoRepository.deleteAll();
    }

    @Test
    void deveListarTodosOsEstados() {
        Estado estado1 = criarEstado("São Paulo");
        Estado estado2 = criarEstado("Goiás");

        estadoRepository.save(estado1);
        estadoRepository.save(estado2);

        var resultado = estadoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("São Paulo")));
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Goiás")));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremEstados() {
        var resultado = estadoService.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    private Estado criarEstado(String nome) {
        Estado estado = new Estado();
        estado.setNome(nome);

        return estado;
    }
}

package com.marcoswolf.crm.reparos.business.reparo;

import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReparoUnitTest {

    @Mock
    private ReparoRepository reparoRepository;

    @InjectMocks
    private ReparoService service;

    @Test
    void deveListarTodosReparos() {
        var reparo1 = new Reparo();
        var reparo2 = new Reparo();
        reparo1.setDescricaoProblema("Não dá imagem");
        reparo2.setDescricaoProblema("Cheiro de queimado e não liga");

        when(reparoRepository.findAllCompletos()).thenReturn(List.of(reparo1, reparo2));

        var resultado = service.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Não dá imagem", resultado.get(0).getDescricaoProblema());
        assertEquals("Cheiro de queimado e não liga", resultado.get(1).getDescricaoProblema());
        verify(reparoRepository, times(1)).findAllCompletos();
    }

    @Test
    void deveSalvarUmNovoReparo() {
        var reparo = new Reparo();
        reparo.setDescricaoProblema("Algumas teclas não funcionam");

        when(reparoRepository.saveAndFlush(reparo)).thenReturn(reparo);

        service.salvar(reparo);

        verify(reparoRepository, times(1)).saveAndFlush(reparo);
    }

    @Test
    void deveDeletarUmReparo() {
        var reparo = new Reparo();
        reparo.setDescricaoProblema("Microfone chiando demais");

        when(reparoRepository.findByIdComPagamentoEPecas(1L)).thenReturn(java.util.Optional.of(reparo));

        service.deletar(1L);

        verify(reparoRepository, times(1)).delete(reparo);
    }

    @Test
    void deveLancarUmaExcecaoAoExcluirUmReparoInexistente() {
        when(reparoRepository.findByIdComPagamentoEPecas(999L)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> service.deletar(999L));
        verify(reparoRepository, never()).delete(any());
    }
}

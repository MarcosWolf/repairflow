package com.marcoswolf.crm.reparos.business.statusReparo;

import com.marcoswolf.crm.reparos.infrastructure.entities.StatusReparo;
import com.marcoswolf.crm.reparos.infrastructure.repositories.StatusReparoRepository;
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
public class StatusReparoUnitTest {

    @Mock
    private StatusReparoRepository statusReparoRepository;

    @InjectMocks
    private StatusReparoService service;

    @Test
    void deveListarTodosStatusReparo() {
        var status1 = new StatusReparo();
        var status2 = new StatusReparo();
        status1.setNome("Em andamento");
        status2.setNome("Concluído");

        when(statusReparoRepository.findAll()).thenReturn(List.of(status1, status2));

        var resultado = service.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Em andamento", resultado.get(0).getNome());
        assertEquals("Concluído", resultado.get(1).getNome());
        verify(statusReparoRepository, times(1)).findAll();
    }

    @Test
    void deveSalvarUmNovoStatusReparo() {
        var statusReparo = new StatusReparo();
        statusReparo.setNome("Aguardando análise");

        when(statusReparoRepository.saveAndFlush(statusReparo)).thenReturn(statusReparo);

        service.salvar(statusReparo);;

        verify(statusReparoRepository, times(1)).saveAndFlush(statusReparo);
    }

    @Test
    void deveDeletarUmStatusReparo() {
        var statusReparo = new StatusReparo();
        statusReparo.setNome("Aguardando pagamento");

        when(statusReparoRepository.findById(1L)).thenReturn(java.util.Optional.of(statusReparo));

        service.deletar(1L);

        verify(statusReparoRepository, times(1)).delete(statusReparo);
    }

    @Test
    void deveLancarExcecaoAoExcluirUmStatusReparoInexistente() {
        when(statusReparoRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> service.deletar(999L));
        verify(statusReparoRepository, never()).delete(any());
    }
}

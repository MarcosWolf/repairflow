package com.marcoswolf.crm.reparos.business.estado;

import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EstadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstadoUnitTest {

    @Mock
    private EstadoRepository estadoRepository;

    @InjectMocks
    private EstadoService service;

    @Test
    void deveListarTodosOsEstados() {
        var estado1 = new Estado();
        var estado2 = new Estado();
        estado1.setNome("São Paulo");
        estado2.setNome("Goiás");

        when(estadoRepository.findAll()).thenReturn(List.of(estado1, estado2));

        var resultado = service.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("São Paulo", resultado.get(0).getNome());
        assertEquals("Goiás", resultado.get(1).getNome());
        verify(estadoRepository, times(1)).findAll();
    }
}

package com.marcoswolf.repairflow.ui.handler.statusReparo.validator;

import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import com.marcoswolf.repairflow.infrastructure.repositories.StatusReparoRepository;
import com.marcoswolf.repairflow.ui.handler.statusReparo.dto.StatusReparoFormData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatusReparoSalvarValidatorTest {
    @Mock
    private StatusReparoRepository statusReparoRepository;

    @InjectMocks
    private StatusReparoSalvarValidator validator;

    @Test
    void deveLancarExcecaoQuandoNomeVazio() {
        var data = new StatusReparoFormData(
                ""
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo nome é obrigatório.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNomeJaExiste() {
        var data = new StatusReparoFormData(
                "Em andamento"
        );

        when(statusReparoRepository.existsByNomeAndNotId("Em andamento", null))
                .thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("Já existe um status de reparo cadastrado com este nome.",
                exception.getMessage()
        );

        verify(statusReparoRepository, times(1))
                .existsByNomeAndNotId("Em andamento", null);
    }

    @Test
    void deveLancarExcecaoQuandoNomeJaExisteAoEditar() {
        var data = new StatusReparoFormData(
                "Em andamento"
        );

        var statusExistente = new StatusReparo();
        statusExistente.setId(1L);

        when(statusReparoRepository.existsByNomeAndNotId("Em andamento", 1L))
                .thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, statusExistente)
        );

        assertEquals("Já existe um status de reparo cadastrado com este nome.",
                exception.getMessage()
        );

        verify(statusReparoRepository, times(1))
                .existsByNomeAndNotId("Em andamento", 1L);
    }
}

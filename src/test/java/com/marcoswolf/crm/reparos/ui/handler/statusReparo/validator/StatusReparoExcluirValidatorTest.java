package com.marcoswolf.crm.reparos.ui.handler.statusReparo.validator;

import com.marcoswolf.crm.reparos.infrastructure.entities.StatusReparo;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatusReparoExcluirValidatorTest {
    @Mock
    private ReparoRepository reparoRepository;

    @InjectMocks
    private StatusReparoExcluirValidator validator;

    @Test
    void deveLancarExcecaoQuandoPossuirReparo() {
        var statusReparo = new StatusReparo();
        statusReparo.setId(1L);
        statusReparo.setNome("Em andamento");

        when(reparoRepository.existsByStatus_Id(statusReparo.getId()))
                .thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(null, statusReparo)
        );

        assertEquals("Não é possível excluir o status de reparo: existe reparo associado.",
                exception.getMessage()
        );

        verify(reparoRepository, times(1))
                .existsByStatus_Id(statusReparo.getId());
    }
}

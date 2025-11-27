package com.marcoswolf.repairflow.ui.handler.tipoEquipamento.validator;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.repairflow.infrastructure.repositories.EquipamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TipoEquipamentoExcluirValidatorTest {
    @Mock private EquipamentoRepository equipamentoRepository;

    @InjectMocks TipoEquipamentoExcluirValidator validator;

    @Test
    void deveLancarExcecaoQuandoPossuirEquipamento() {
        var tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setId(1L);
        tipoEquipamento.setNome("Teclado");

        when(equipamentoRepository.existsByTipoEquipamentoId(tipoEquipamento.getId()))
                .thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(null, tipoEquipamento)
        );

        assertEquals("Não é possível excluir o tipo de equipamento: existe equipamento associado.",
                exception.getMessage());

        verify(equipamentoRepository, times(1))
                .existsByTipoEquipamentoId(tipoEquipamento.getId());
    }
}

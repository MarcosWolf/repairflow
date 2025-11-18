package com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.validator;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.TipoEquipamentoRepository;
import com.marcoswolf.crm.reparos.ui.handler.tipoEquipamento.dto.TipoEquipamentoFormData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TipoEquipamentoSalvarValidatorTest {
    @Mock
    TipoEquipamentoRepository tipoEquipamentoRepository;

    @InjectMocks
    private TipoEquipamentoSalvarValidator validator;

    @Test
    void deveLancarExcecaoQuandoNomeVazio() {
        var data = new TipoEquipamentoFormData(
                ""
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo nome é obrigatório.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNomeJaExiste() {
        var data = new TipoEquipamentoFormData(
                "Amplificador"
        );

        when(tipoEquipamentoRepository.existsByNomeAndNotId("Amplificador", null))
                .thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("Já existe um tipo de equipamento cadastrado com este nome.", exception.getMessage());

        verify(tipoEquipamentoRepository, times(1))
                .existsByNomeAndNotId("Amplificador", null);
    }
    @Test
    void deveLancarExcecaoQuandoNomeJaExisteAoEditar() {
        var data = new TipoEquipamentoFormData(
                "Amplificador"
        );

        var tipoExistente = new TipoEquipamento();
        tipoExistente.setId(1L);

        when(tipoEquipamentoRepository.existsByNomeAndNotId("Amplificador", 1L))
                .thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, tipoExistente)
        );

        assertEquals("Já existe um tipo de equipamento cadastrado com este nome.", exception.getMessage());

        verify(tipoEquipamentoRepository, times(1))
                .existsByNomeAndNotId("Amplificador", 1L);
    }

}

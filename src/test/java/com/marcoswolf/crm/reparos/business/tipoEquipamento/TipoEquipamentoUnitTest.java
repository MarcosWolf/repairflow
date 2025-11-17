package com.marcoswolf.crm.reparos.business.tipoEquipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.TipoEquipamentoRepository;
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
public class TipoEquipamentoUnitTest {

    @Mock
    private TipoEquipamentoRepository tipoEquipamentoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private TipoEquipamentoService service;

    @Test
    void deveListarTodosTipoEquipamentos() {
        var tipoEquipamento1 = new TipoEquipamento();
        var tipoEquipamento2 = new TipoEquipamento();
        tipoEquipamento1.setNome("Mesa de Som");
        tipoEquipamento2.setNome("Caixa Amplificadora");

        when(tipoEquipamentoRepository.findAll()).thenReturn(List.of(tipoEquipamento1, tipoEquipamento2));

        var resultado = service.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Mesa de Som", resultado.get(0).getNome());
        assertEquals("Caixa Amplificadora", resultado.get(1).getNome());
        verify(tipoEquipamentoRepository, times(1)).findAll();
    }

    @Test
    void deveSalvarUmNovoTipoEquipamento() {
        var tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setNome("Teclado Digital");

        when(tipoEquipamentoRepository.saveAndFlush(tipoEquipamento)).thenReturn(tipoEquipamento);

        service.salvar(tipoEquipamento);

        verify(tipoEquipamentoRepository, times(1)).saveAndFlush(tipoEquipamento);
    }

    @Test
    void deveDeletarUmTipoEquipamento() {
        var tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setNome("Guitarra");

        when(tipoEquipamentoRepository.findById(1L)).thenReturn(java.util.Optional.of(tipoEquipamento));

        service.deletar(1L);

        verify(tipoEquipamentoRepository, times(1)).delete(tipoEquipamento);
    }

    @Test
    void deveLancarExcecaoAoExcluirUmTipoEquipamentoInexistente() {
        when(tipoEquipamentoRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> service.deletar(999L));
        verify(tipoEquipamentoRepository, never()).delete(any());
    }
}
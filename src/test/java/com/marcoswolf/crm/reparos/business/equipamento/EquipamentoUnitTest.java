package com.marcoswolf.crm.reparos.business.equipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
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
public class EquipamentoUnitTest {

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @InjectMocks
    private EquipamentoService service;

    @Test
    void deveListarTodosEquipamentos() {
        var equipamento1 = new Equipamento();
        var equipamento2 = new Equipamento();
        equipamento1.setMarca("Pioneer");
        equipamento2.setMarca("Behringer");

        when(equipamentoRepository.findAll()).thenReturn(List.of(equipamento1, equipamento2));;

        var resultado = service.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Pioneer", resultado.get(0).getMarca());
        assertEquals("Behringer", resultado.get(1).getMarca());
        verify(equipamentoRepository, times(1)).findAll();
    }

    @Test
    void deveSalvarUmNovoEquipamento() {
        var equipamento = new Equipamento();
        equipamento.setMarca("Sony");

        when(equipamentoRepository.saveAndFlush(equipamento)).thenReturn(equipamento);

        service.salvar(equipamento);

        verify(equipamentoRepository, times(1)).saveAndFlush(equipamento);
    }

    @Test
    void deveDeletarUmEquipamento() {
        var equipamento = new Equipamento();
        equipamento.setMarca("ZNX");

        when(equipamentoRepository.findById(1L)).thenReturn(java.util.Optional.of(equipamento));

        service.deletar(1L);

        verify(equipamentoRepository, times(1)).delete(equipamento);
    }

    @Test
    void deveLancarExcecaoAoExcluirUmEquipamentoInexistente() {
        when(equipamentoRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> service.deletar(999L));
        verify(equipamentoRepository, never()).delete(any());
    }
}

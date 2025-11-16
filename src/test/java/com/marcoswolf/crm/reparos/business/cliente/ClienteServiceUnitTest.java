package com.marcoswolf.crm.reparos.business.cliente;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
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
public class ClienteServiceUnitTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void deveListarTodosClientes() {
        var cliente1 = new Cliente();
        cliente1.setNome("Marcos");
        var cliente2 = new Cliente();
        cliente2.setNome("Alan");

        when(clienteRepository.findAll()).thenReturn(List.of(cliente1, cliente2));

        var resultado = clienteService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Marcos", resultado.get(0).getNome());
        assertEquals("Alan", resultado.get(1).getNome());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void deveSalvarUmNovoCliente() {
        var cliente = new Cliente();
        cliente.setNome("Anderson");

        when(clienteRepository.saveAndFlush(cliente)).thenReturn(cliente);

        clienteService.salvar(cliente);

        verify(clienteRepository, times(1)).saveAndFlush(cliente);
    }

    @Test
    void deveDeletarUmCliente() {
        var cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Pedro");

        when(clienteRepository.findById(1L)).thenReturn(java.util.Optional.of(cliente));

        clienteService.deletar(1L);

        verify(clienteRepository, times(1)).delete(cliente);
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExistir() {
        when(clienteRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> clienteService.deletar(999L));
        verify(clienteRepository, never()).delete(any());
    }
}

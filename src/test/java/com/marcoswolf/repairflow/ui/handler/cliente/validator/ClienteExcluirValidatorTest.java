package com.marcoswolf.repairflow.ui.handler.cliente.validator;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.repairflow.infrastructure.repositories.ReparoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteExcluirValidatorTest {
    @Mock private ReparoRepository reparoRepository;
    @Mock private EquipamentoRepository equipamentoRepository;

    @InjectMocks
    private ClienteExcluirValidator validator;

    @Test
    void deveLancarExcecaoQuandoPossuirReparo() {
        var cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Marcos");

        when(reparoRepository.existsByEquipamento_Cliente_Id(cliente.getId()))
                .thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(null, cliente)
        );

        assertEquals("Não é possível excluir o cliente: existe reparo associado.", exception.getMessage());

        verify(reparoRepository, times(1))
                .existsByEquipamento_Cliente_Id(cliente.getId());
    }

    @Test
    void deveLancarExcecaoQuandoPossuirEquipamento() {
        var cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Marcos");

        when(equipamentoRepository.existsByClienteId(cliente.getId()))
                .thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(null, cliente)
        );

        assertEquals("Não é possível excluir o cliente: existe equipamento associado.", exception.getMessage());

        verify(equipamentoRepository, times(1))
                .existsByClienteId(cliente.getId());
    }
}

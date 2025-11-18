package com.marcoswolf.crm.reparos.ui.handler.equipamento.validator;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EquipamentoExcluirValidatorTest {
    @Mock
    private ReparoRepository reparoRepository;

    @InjectMocks
    private EquipamentoExcluirValidator validator;

    @Test
    void deveLancarExcecaoQuandoPossuirReparo() {
        var equipamento = new Equipamento();
        equipamento.setId(1L);
        equipamento.setMarca("Ibanez");

        when(reparoRepository.existsByEquipamento_Id(equipamento.getId()))
                .thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(null, equipamento)
        );

        assertEquals("Não é possível excluir o equipamento: existe reparo associado.",
                exception.getMessage()
        );
    }
}

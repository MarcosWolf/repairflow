package com.marcoswolf.crm.reparos.ui.handler.equipamento.validator;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.crm.reparos.ui.handler.equipamento.dto.EquipamentoFormData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class EquipamentoSalvarValidatorTest {
    @InjectMocks
    private EquipamentoSalvarValidator validator;

    @Test
    void deveLancarExcecaoQuandoTipoEquipamentoVazio() {
        var tipoEquipamento = new TipoEquipamento();

        var cliente = new Cliente();
        cliente.setNome("Marcos");
        cliente.setId(1L);

        var data = new EquipamentoFormData(
                tipoEquipamento,
                "Ibanez",
                "RX323",
                "RX323-213123",
                cliente
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo tipo de equipamento é obrigatório.",
        exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoMarcaVazio() {
        var tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setNome("Guitarra");
        tipoEquipamento.setId(1L);

        var cliente = new Cliente();
        cliente.setNome("Marcos");
        cliente.setId(1L);

        var data = new EquipamentoFormData(
                tipoEquipamento,
                "",
                "RX323",
                "RX323-213123",
                cliente
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo marca é obrigatório.",
                exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoModeloVazio() {
        var tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setNome("Guitarra");
        tipoEquipamento.setId(1L);

        var cliente = new Cliente();
        cliente.setNome("Marcos");
        cliente.setId(1L);

        var data = new EquipamentoFormData(
                tipoEquipamento,
                "Ibanez",
                "",
                "RX323-213123",
                cliente
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo modelo é obrigatório.",
                exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoClienteVazio() {
        var tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setNome("Guitarra");
        tipoEquipamento.setId(1L);

        var cliente = new Cliente();

        var data = new EquipamentoFormData(
                tipoEquipamento,
                "Ibanez",
                "RX323",
                "RX323-213123",
                cliente
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo cliente é obrigatório.",
                exception.getMessage());
    }
}

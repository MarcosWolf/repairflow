package com.marcoswolf.repairflow.ui.handler.reparo.validator;

import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.infrastructure.entities.PecaPagamento;
import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import com.marcoswolf.repairflow.ui.handler.reparo.dto.ReparoFormData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ReparoSalvarValidatorTest {
    @InjectMocks
    private ReparoSalvarValidator validator;

    @Test
    void deveLancarExcecaoQuandoEquipamentoVazio() {
        var equipamento = new Equipamento();

        var status = new StatusReparo();
        status.setId(1L);
        status.setNome("Concluído");

        List <PecaPagamento> pecas = new ArrayList<>();
        var p1 = new PecaPagamento();
        p1.setId(1L);
        p1.setQuantidade(3);
        p1.setValor(new BigDecimal("300.00"));
        pecas.add(p1);

        var data = new ReparoFormData(
                equipamento,
                LocalDate.parse("2025-11-18"),
                LocalDate.parse("2025-11-20"),
                "Descrição do problema",
                "Serviço executado",
                status,
                new BigDecimal("200.00"),
                new BigDecimal("20.00"),
                LocalDate.parse("2025-11-20"),
                pecas
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo equipamento é obrigatório.",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoStatusVazio() {
        var equipamento = new Equipamento();
        equipamento.setId(1L);
        equipamento.setMarca("Marca");

        var status = new StatusReparo();

        List <PecaPagamento> pecas = new ArrayList<>();
        var p1 = new PecaPagamento();
        p1.setId(1L);
        p1.setQuantidade(3);
        p1.setValor(new BigDecimal("300.00"));
        pecas.add(p1);

        var data = new ReparoFormData(
                equipamento,
                LocalDate.parse("2025-11-18"),
                LocalDate.parse("2025-11-20"),
                "Descrição do problema",
                "Serviço executado",
                status,
                new BigDecimal("200.00"),
                new BigDecimal("20.00"),
                LocalDate.parse("2025-11-20"),
                pecas
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo status é obrigatório.",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoDataEntradaVazio() {
        var equipamento = new Equipamento();
        equipamento.setId(1L);
        equipamento.setMarca("Marca");

        var status = new StatusReparo();
        status.setId(1L);
        status.setNome("Concluído");

        List <PecaPagamento> pecas = new ArrayList<>();
        var p1 = new PecaPagamento();
        p1.setId(1L);
        p1.setQuantidade(3);
        p1.setValor(new BigDecimal("300.00"));
        pecas.add(p1);

        var data = new ReparoFormData(
                equipamento,
                null,
                LocalDate.parse("2025-11-20"),
                "Descrição do problema",
                "Serviço executado",
                status,
                new BigDecimal("200.00"),
                new BigDecimal("20.00"),
                LocalDate.parse("2025-11-20"),
                pecas
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo data de entrada é obrigatório.",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoDescricaoDefeitoVazio() {
        var equipamento = new Equipamento();
        equipamento.setId(1L);
        equipamento.setMarca("Marca");

        var status = new StatusReparo();
        status.setId(1L);
        status.setNome("Concluído");

        List <PecaPagamento> pecas = new ArrayList<>();
        var p1 = new PecaPagamento();
        p1.setId(1L);
        p1.setQuantidade(3);
        p1.setValor(new BigDecimal("300.00"));
        pecas.add(p1);

        var data = new ReparoFormData(
                equipamento,
                LocalDate.parse("2025-11-18"),
                LocalDate.parse("2025-11-20"),
                "",
                "Serviço executado",
                status,
                new BigDecimal("200.00"),
                new BigDecimal("20.00"),
                LocalDate.parse("2025-11-20"),
                pecas
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo descrição do defeito é obrigatório.",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoValorTotalNegativo() {
        var equipamento = new Equipamento();
        equipamento.setId(1L);
        equipamento.setMarca("Marca");

        var status = new StatusReparo();
        status.setId(1L);
        status.setNome("Concluído");

        List <PecaPagamento> pecas = new ArrayList<>();

        var data = new ReparoFormData(
                equipamento,
                LocalDate.parse("2025-11-18"),
                LocalDate.parse("2025-11-20"),
                "Descricao Problema",
                "Serviço executado",
                status,
                new BigDecimal("100.00"),
                new BigDecimal("600.00"),
                LocalDate.parse("2025-11-20"),
                pecas
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O valor total não pode ser negativo.",
                exception.getMessage()
        );
    }
}

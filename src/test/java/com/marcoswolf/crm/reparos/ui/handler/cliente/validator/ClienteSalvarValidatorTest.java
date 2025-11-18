package com.marcoswolf.crm.reparos.ui.handler.cliente.validator;

import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.crm.reparos.ui.handler.cliente.dto.ClienteFormData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteSalvarValidatorTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteSalvarValidator validator;

    @Test
    void deveLancarExcecaoQuandoNomeVazio() {
        var estado = new Estado();
        estado.setId(1L);
        estado.setNome("São Paulo");

        var data = new ClienteFormData(
            "",
                "(13) 91234-5678",
                "marcos@gmail.com",
                "São Paulo",
                "Liberdade",
                "11111-234",
                "Rua das Flores",
                123,
                estado
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo nome é obrigatório.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoTelefoneVazio() {
        var estado = new Estado();
        estado.setId(1L);
        estado.setNome("São Paulo");

        var data = new ClienteFormData(
                "Marcos",
                "",
                "marcos@gmail.com",
                "São Paulo",
                "Liberdade",
                "11111-234",
                "Rua das Flores",
                123,
                estado
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo telefone é obrigatório.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoTelefoneInvalido() {
        var estado = new Estado();
        estado.setId(1L);
        estado.setNome("São Paulo");

        var data = new ClienteFormData(
                "Marcos",
                "(13) 3421-12",
                "marcos@gmail.com",
                "São Paulo",
                "Liberdade",
                "11111-234",
                "Rua das Flores",
                123,
                estado
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O telefone é inválido.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEmailInvalido() {
        var estado = new Estado();
        estado.setId(1L);
        estado.setNome("São Paulo");

        var data = new ClienteFormData(
                "Marcos",
                "(13) 3421-1212",
                "marcos.gmail.com",
                "São Paulo",
                "Liberdade",
                "11111-234",
                "Rua das Flores",
                123,
                estado
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O e-mail é inválido.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoTelefoneJaExiste() {
        var estado = new Estado();
        estado.setId(1L);
        estado.setNome("São Paulo");

        var data = new ClienteFormData(
                "Marcos",
                "(13) 3421-1212",
                "marcos@gmail.com",
                "São Paulo",
                "Liberdade",
                "11111-234",
                "Rua das Flores",
                123,
                estado
        );

        when(clienteRepository.existsByTelefoneAndNotId("(13) 3421-1212", null))
                .thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("Já existe um cliente cadastrado com este telefone.", exception.getMessage());

        verify(clienteRepository, times(1))
                .existsByTelefoneAndNotId("(13) 3421-1212", null);
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExiste() {
        var estado = new Estado();
        estado.setId(1L);
        estado.setNome("São Paulo");

        var data = new ClienteFormData(
                "Marcos",
                "(13) 3421-1212",
                "marcos@gmail.com",
                "São Paulo",
                "Liberdade",
                "11111-234",
                "Rua das Flores",
                123,
                estado
        );

        when(clienteRepository.existsByEmailAndNotId("marcos@gmail.com", null))
                .thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("Já existe um cliente cadastrado com este e-mail.", exception.getMessage());

        verify(clienteRepository, times(1))
                .existsByEmailAndNotId("marcos@gmail.com", null);
    }

    @Test
    void deveLancarExcecaoQuandoCidadeVazia() {
        var estado = new Estado();
        estado.setId(1L);
        estado.setNome("São Paulo");

        var data = new ClienteFormData(
                "Marcos",
                "(13) 3421-1212",
                "marcos@gmail.com",
                "",
                "Liberdade",
                "11111-234",
                "Rua das Flores",
                123,
                estado
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo cidade é obrigatório.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEstadoVazio() {
        var estado = new Estado();
        estado.setId(0L);
        estado.setNome("Selecione");

        var data = new ClienteFormData(
                "Marcos",
                "(13) 3421-1212",
                "marcos@gmail.com",
                "São Paulo",
                "Liberdade",
                "11111-234",
                "Rua das Flores",
                123,
                estado
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O campo estado é obrigatório.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCepInvalido() {
        var estado = new Estado();
        estado.setId(1L);
        estado.setNome("São Paulo");

        var data = new ClienteFormData(
                "Marcos",
                "(13) 3421-1212",
                "marcos@gmail.com",
                "São Paulo",
                "Liberdade",
                "11111",
                "Rua das Flores",
                123,
                estado
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validar(data, null)
        );

        assertEquals("O CEP é inválido.", exception.getMessage());
    }
}

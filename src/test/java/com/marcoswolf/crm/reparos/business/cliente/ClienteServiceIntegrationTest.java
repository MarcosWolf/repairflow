package com.marcoswolf.crm.reparos.business.cliente;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Endereco;
import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EstadoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ClienteServiceIntegrationTest {
    @Autowired private ClienteService clienteService;

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private EstadoRepository estadoRepository;

    @AfterEach
    void limparBanco() {
        clienteRepository.deleteAll();
    }

    @Test
    void deveListarTodosOsClientes() {
        Cliente cliente1 = criarCliente("Marcos", "13912345678", "viniciosramos.dev@gmail.com",
                "São Paulo", "São Paulo", "12345-678",
                "Belas Artes", "Rua do Pássaro", 19
        );
        Cliente cliente2 = criarCliente("Paulo", "11923456789", "paulo@gmail.com",
                "Curitiba", "Paraná", "11234-567",
                "Martin Afonso", "Rua dos Imigrantes", 192
        );

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        var resultado = clienteService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Marcos")));
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Paulo")));
        assertTrue(resultado.stream().anyMatch(c -> c.getEndereco().getEstado().getNome().equals("Paraná")));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremClientes() {
        var resultado = clienteService.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveSalvarUmNovoCliente() {
        Cliente cliente = criarCliente(
                "Anderson", "13912345678", "anderson@gmail.com",
                "Manaus", "Amazonas", "13212-123", "Laranja",
                "Avenida Presidente Kennedy", 602
        );
        clienteService.salvar(cliente);

        var clienteSalvo = clienteRepository.findAll().get(0);
        assertNotNull(clienteSalvo.getId());
        assertEquals("Anderson", clienteSalvo.getNome());
        assertEquals("13912345678", clienteSalvo.getTelefone());
        assertEquals("anderson@gmail.com", clienteSalvo.getEmail());
        assertEquals("Manaus", clienteSalvo.getEndereco().getCidade());
        assertEquals("Amazonas", clienteSalvo.getEndereco().getEstado().getNome());
        assertEquals("13212-123", clienteSalvo.getEndereco().getCep());
        assertEquals("Laranja", clienteSalvo.getEndereco().getBairro());
        assertEquals("Avenida Presidente Kennedy", clienteSalvo.getEndereco().getLogradouro());
        assertEquals(602, clienteSalvo.getEndereco().getNumero());
    }

    @Test
    void deveAtualizarUmCliente() {
        Cliente cliente = criarCliente("Alan", "13952345678", "alan@gmail.com",
                "Pedro de Toledo", "São Paulo", "11132-555",
                "", "", null
        );
        clienteService.salvar(cliente);

        Endereco endereco = cliente.getEndereco();
        endereco.setCidade("Ubatuba");
        endereco.setCep("23143-312");
        endereco.setBairro("Boa Vista");
        endereco.setLogradouro("Rua das Flores");
        endereco.setNumero(321);

        cliente.setEndereco(endereco);
        clienteService.salvar(cliente);

        var clienteAtualizado = clienteRepository.findById(cliente.getId()).orElseThrow();

        assertNotNull(cliente);
        assertEquals("Ubatuba", clienteAtualizado.getEndereco().getCidade());
        assertEquals("23143-312", clienteAtualizado.getEndereco().getCep());
        assertEquals("Boa Vista", clienteAtualizado.getEndereco().getBairro());
        assertEquals("Rua das Flores", clienteAtualizado.getEndereco().getLogradouro());
        assertEquals(321, clienteAtualizado.getEndereco().getNumero());
    }

    @Test
    void deveDeletarUmCliente() {
        Cliente cliente = criarCliente("Mariana", "13923456789", "",
                "Goiânia", "Goiás", "12312-532",
                "", "", null
        );
        clienteService.salvar(cliente);

        Long clienteId = cliente.getId();

        clienteService.deletar(clienteId);

        assertFalse(clienteRepository.existsById(clienteId));
    }

    @Test
    void deveLancarExcecaoAoExcluirUmClienteInexistente() {
        var exception = assertThrows(RuntimeException.class,
                () -> clienteService.deletar(999L)
        );

        assertEquals("Cliente não encontrado.", exception.getMessage());
    }

    private Cliente criarCliente(
            String nome, String telefone, String email,
            String cidade, String estado, String cep,
            String bairro, String logradouro, Integer numero
    ) {
        Estado estadoAux = new Estado();
        estadoAux.setNome(estado);
        estadoRepository.save(estadoAux);

        Endereco endereco = new Endereco();
        endereco.setCidade(cidade);
        endereco.setEstado(estadoAux);
        endereco.setCep(cep);
        endereco.setBairro(bairro);
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);
        cliente.setEmail(email);

        return cliente;
    }
}
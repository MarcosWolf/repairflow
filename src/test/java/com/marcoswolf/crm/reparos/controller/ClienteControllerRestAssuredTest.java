package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Endereco;
import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EstadoRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ClienteControllerRestAssuredTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/cliente";

        clienteRepository.deleteAll();
        estadoRepository.deleteAll();

        Estado estado = new Estado();
        estado.setNome("São Paulo");
        estadoRepository.save(estado);
    }

    @Test
    void deveCriarNovoCliente() {
        Cliente cliente = criarClienteCompleto();

        given()
                .contentType(ContentType.JSON)
                .body(cliente)
        .when()
                .post()
        .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .header("Location", matchesPattern(".*/cliente/\\d+"))
                .body("nome", equalTo("Marcos Vinícios"))
                .body("telefone", equalTo("(13) 98131-4531"))
                .body("email", equalTo("viniciosramos.dev@gmail.com"))
                .body("id", notNullValue());
    }

    @Test
    void deveListarTodos() {
        clienteRepository.saveAndFlush(criarClienteCompleto());

        given()
        .when()
                .get()
        .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].nome", equalTo("Marcos Vinícios"));
    }

    @Test
    void deveBuscarPorId() {
        Cliente clienteSalvo = clienteRepository.saveAndFlush(criarClienteCompleto());

        given()
        .when()
                .get("/{id}", clienteSalvo.getId())
        .then()
                .statusCode(200)
                .body("id", equalTo(clienteSalvo.getId().intValue()))
                .body("nome", equalTo("Marcos Vinícios"));
    }

    @Test
    void deveRetornarNotFoundAoBuscarInexistente() {
        given()
        .when()
                .get("/999")
        .then()
                .statusCode(404);
    }

    @Test
    void deveDeletarCliente() {
        Cliente clienteSalvo = clienteRepository.saveAndFlush(criarClienteCompleto());

        given()
        .when()
                .delete("/{id}", clienteSalvo.getId())
        .then()
                .statusCode(204);
    }

    @Test
    void deveRetornarBadRequestAoDeletarInexistente() {
        given()
        .when()
                .delete("/999")
        .then()
                .statusCode(400);
    }

    private Cliente criarClienteCompleto() {
        Cliente cliente = new Cliente();
        cliente.setNome("Marcos Vinícios");
        cliente.setTelefone("(13) 98131-4531");
        cliente.setEmail("viniciosramos.dev@gmail.com");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Teste");
        endereco.setNumero(123);
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setCep("01234-567");
        endereco.setEstado(estadoRepository.findAll().get(0));

        cliente.setEndereco(endereco);
        return cliente;
    }

}

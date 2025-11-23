package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.controller.dto.ClienteRequestDTO;
import com.marcoswolf.crm.reparos.controller.mappers.ClienteRequestMapper;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Endereco;
import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EstadoRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

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

    @Autowired
    private ClienteRequestMapper mapper;

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
        var requestDTO = criarClienteCompletoDTO();

        given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
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
        clienteRepository.saveAndFlush(mapper.toEntity(criarClienteCompletoDTO()));

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
        Cliente clienteSalvo = clienteRepository.saveAndFlush(mapper.toEntity(criarClienteCompletoDTO()));

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
        Cliente clienteSalvo = clienteRepository.saveAndFlush(mapper.toEntity(criarClienteCompletoDTO()));

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

    private ClienteRequestDTO criarClienteCompletoDTO() {
        Long estadoId = estadoRepository.findAll().get(0).getId();

        ClienteRequestDTO.EnderecoDTO enderecoDTO = new ClienteRequestDTO.EnderecoDTO(
                "Rua Teste",
                123,
                "Centro",
                "São Paulo",
                "01234-567",
                estadoId
        );

        return new ClienteRequestDTO(
                "Marcos Vinícios",
                "(13) 98131-4531",
                "viniciosramos.dev@gmail.com",
                enderecoDTO
        );
    }

    private Cliente toEntity(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setEmail(dto.email());

        Endereco endereco = new Endereco();
        endereco.setCidade(dto.endereco().cidade());
        endereco.setBairro(dto.endereco().bairro());
        endereco.setCep(dto.endereco().cep());
        endereco.setLogradouro(dto.endereco().logradouro());
        endereco.setNumero(dto.endereco().numero());

        Estado estado = estadoRepository.findById(dto.endereco().estadoId())
                .orElseThrow();

        endereco.setEstado(estado);
        cliente.setEndereco(endereco);

        return cliente;
    }
}

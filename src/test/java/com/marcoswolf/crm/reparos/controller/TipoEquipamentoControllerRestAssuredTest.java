package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.controller.dto.TipoEquipamentoRequestDTO;
import com.marcoswolf.crm.reparos.controller.mappers.TipoEquipamentoRequestMapper;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.TipoEquipamentoRepository;
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
public class TipoEquipamentoControllerRestAssuredTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TipoEquipamentoRepository tipoEquipamentoRepository;

    @Autowired
    private TipoEquipamentoRequestMapper mapper;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/tipo-equipamento";

        tipoEquipamentoRepository.deleteAll();
    }

    @Test
    void deveCriarNovoTipoEquipamento() {
        var requestDTO = criarTipoEquipamentoCompletoDTO();

        given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
        .when()
                .post()
        .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .header("Location", matchesPattern(".*/tipo-equipamento/\\d+"))
                .body("nome", equalTo("Amplificador"))
                .body("id", notNullValue());
    }

    @Test
    void deveListarTodos() {
        tipoEquipamentoRepository.saveAndFlush(mapper.toEntity(criarTipoEquipamentoCompletoDTO()));

        given()
        .when()
                .get()
        .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].nome", equalTo("Amplificador"));
    }

    @Test
    void deveBuscarPorId() {
        TipoEquipamento tipoEquipamentoSalvo = tipoEquipamentoRepository.saveAndFlush(mapper.toEntity(criarTipoEquipamentoCompletoDTO()));

        given()
        .when()
                .get("/{id}", tipoEquipamentoSalvo.getId())
        .then()
                .statusCode(200)
                .body("id", equalTo(tipoEquipamentoSalvo.getId().intValue()))
                .body("nome", equalTo("Amplificador"));
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
    void deveDeletarTipoEquipamento() {
        TipoEquipamento tipoEquipamentoSalvo = tipoEquipamentoRepository.saveAndFlush(mapper.toEntity(criarTipoEquipamentoCompletoDTO()));

        given()
        .when()
                .delete("/{id}", tipoEquipamentoSalvo.getId())
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

    private TipoEquipamentoRequestDTO criarTipoEquipamentoCompletoDTO() {
        return new TipoEquipamentoRequestDTO(
                "Amplificador"
        );
    }
}

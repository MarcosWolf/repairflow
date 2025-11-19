package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.controller.dto.StatusReparoRequestDTO;
import com.marcoswolf.crm.reparos.infrastructure.entities.StatusReparo;
import com.marcoswolf.crm.reparos.infrastructure.repositories.StatusReparoRepository;
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
public class StatusReparoControllerRestAssuredTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StatusReparoRepository statusReparoRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/status-reparo";

        statusReparoRepository.deleteAll();
    }

    @Test
    void deveCriarNovoStatusReparo() {
        var requestDTO = criarStatusReparoCompletoDTO();

        given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
        .when()
                .post()
        .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .header("Location", matchesPattern(".*/status-reparo/\\d+"))
                .body("nome", equalTo("Em andamento"))
                .body("id", notNullValue());
    }

    @Test
    void deveListarTodos() {
        statusReparoRepository.saveAndFlush(toEntity(criarStatusReparoCompletoDTO()));

        given()
        .when()
                .get()
        .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].nome", equalTo("Em andamento"));
    }

    @Test
    void deveBuscarPorId() {
        StatusReparo statusReparoSalvo = statusReparoRepository.saveAndFlush(toEntity(criarStatusReparoCompletoDTO()));

        given()
        .when()
                .get("/{id}", statusReparoSalvo.getId())
        .then()
                .statusCode(200)
                .body("id", equalTo(statusReparoSalvo.getId().intValue()))
                .body("nome", equalTo("Em andamento"));
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
    void deveDeletarStatusReparo() {
        StatusReparo statusReparoSalvo = statusReparoRepository.saveAndFlush(toEntity(criarStatusReparoCompletoDTO()));

        given()
        .when()
                .delete("/{id}", statusReparoSalvo.getId())
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

    private StatusReparoRequestDTO criarStatusReparoCompletoDTO() {
        return new StatusReparoRequestDTO(
                "Em andamento"
        );
    }

    private StatusReparo toEntity(StatusReparoRequestDTO dto) {
        StatusReparo statusReparo = new StatusReparo();
        statusReparo.setNome(dto.nome());

        return statusReparo;
    }
}
package com.marcoswolf.repairflow.controller;

import com.marcoswolf.repairflow.controller.dto.PecaPagamentoRequestDTO;
import com.marcoswolf.repairflow.controller.dto.ReparoRequestDTO;
import com.marcoswolf.repairflow.controller.mappers.ReparoRequestMapper;
import com.marcoswolf.repairflow.infrastructure.entities.*;
import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.infrastructure.entities.Reparo;
import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import com.marcoswolf.repairflow.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.repairflow.infrastructure.repositories.ReparoRepository;
import com.marcoswolf.repairflow.infrastructure.repositories.StatusReparoRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReparoControllerRestAssuredTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ReparoRepository reparoRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private StatusReparoRepository statusReparoRepository;

    @Autowired
    private ReparoRequestMapper mapper;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/reparo";

        reparoRepository.deleteAll();
        equipamentoRepository.deleteAll();
        statusReparoRepository.deleteAll();

        StatusReparo status = new StatusReparo();
        status.setNome("Em andamento");
        statusReparoRepository.save(status);

        Equipamento equipamento = new Equipamento();
        equipamento.setMarca("LG");
        equipamento.setModelo("Modelo XPTO");
        equipamento.setNumeroSerie("123456");
        equipamentoRepository.save(equipamento);
    }

    @Test
    void deveCriarNovoReparo() {
        var requestDTO = criarReparoCompletoDTO();

        given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
        .when()
                .post()
        .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .header("Location", matchesPattern(".*/reparo/\\d+"))
                .body("descricaoProblema", equalTo("Equipamento não liga"))
                .body("id", notNullValue());
    }

    @Test
    void deveListarTodos() {
        reparoRepository.saveAndFlush(mapper.toEntity(criarReparoCompletoDTO()));

        given()
        .when()
                .get()
        .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].descricaoProblema", equalTo("Equipamento não liga"));
    }

    @Test
    void deveBuscarPorId() {
        Reparo reparoSalvo = reparoRepository.saveAndFlush(mapper.toEntity(criarReparoCompletoDTO()));

        given()
        .when()
                .get("/{id}", reparoSalvo.getId())
        .then()
                .statusCode(200)
                .body("id", equalTo(reparoSalvo.getId().intValue()))
                .body("descricaoProblema", equalTo("Equipamento não liga"));
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
    void deveDeletarReparo() {
        Reparo reparoSalvo = reparoRepository.saveAndFlush(mapper.toEntity(criarReparoCompletoDTO()));

        given()
        .when()
                .delete("/{id}", reparoSalvo .getId())
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

    private ReparoRequestDTO criarReparoCompletoDTO() {
        Long equipamentoId = equipamentoRepository.findAll().get(0).getId();
        Long statusId = statusReparoRepository.findAll().get(0).getId();

        List<PecaPagamentoRequestDTO> pecas = List.of(
                new PecaPagamentoRequestDTO(
                        1L,
                        "Placa",
                        2,
                        new BigDecimal("50.00")
                )
        );

        return new ReparoRequestDTO(
                equipamentoId,
                LocalDate.now(),
                null,
                "Equipamento não liga",
                "Troca da fonte de alimentação",
                statusId,
                new BigDecimal("150.00"),
                new BigDecimal("0.00"),
                LocalDate.now(),
                pecas
        );
    }
}
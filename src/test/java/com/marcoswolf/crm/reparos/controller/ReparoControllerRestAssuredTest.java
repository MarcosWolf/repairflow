package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.controller.dto.PecaPagamentoRequestDTO;
import com.marcoswolf.crm.reparos.controller.dto.ReparoRequestDTO;
import com.marcoswolf.crm.reparos.infrastructure.entities.*;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.StatusReparoRepository;
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
        reparoRepository.saveAndFlush(toEntity(criarReparoCompletoDTO()));

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
        Reparo reparoSalvo = reparoRepository.saveAndFlush(toEntity(criarReparoCompletoDTO()));

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
        Reparo reparoSalvo = reparoRepository.saveAndFlush(toEntity(criarReparoCompletoDTO()));

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

    private Reparo toEntity(ReparoRequestDTO dto) {
        Equipamento equipamento = equipamentoRepository.findById(dto.equipamentoId())
                .orElseThrow();

        StatusReparo status = statusReparoRepository.findById(dto.statusReparoId())
                .orElseThrow();

        List<PecaPagamento> pecas = dto.pecas().stream()
                .map(p -> {
                    PecaPagamento pp = new PecaPagamento();
                    pp.setQuantidade(p.quantidade());
                    pp.setValor(p.valorUnitario());
                    return pp; // <<< IMPORTANTE: sem ID
                })
                .toList();

        Reparo reparo = new Reparo();
        reparo.setEquipamento(equipamento);
        reparo.setDataEntrada(dto.dataEntrada());
        reparo.setDataSaida(dto.dataSaida());
        reparo.setDescricaoProblema(dto.descricaoProblema());
        reparo.setServicoExecutado(dto.servicoExecutado());
        reparo.setStatus(status);

        Pagamento pagamento = new Pagamento();
        pagamento.setValorServico(dto.valorServico());
        pagamento.setDesconto(dto.desconto());
        pagamento.setDataPagamento(dto.dataPagamento());
        pagamento.setPecas(pecas);

        reparo.setPagamento(pagamento);

        return reparo;
    }
}
package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.controller.dto.EquipamentoRequestDTO;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
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
public class EquipamentoControllerRestAssuredTest {
    @LocalServerPort
    private int port;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private TipoEquipamentoRepository tipoEquipamentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/equipamento";

        equipamentoRepository.deleteAll();
        tipoEquipamentoRepository.deleteAll();
        clienteRepository.deleteAll();

        TipoEquipamento tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setNome("Mesa de DJ");
        tipoEquipamentoRepository.save(tipoEquipamento);

        Cliente cliente = new Cliente();
        cliente.setNome("Marcos Vinícios");
        clienteRepository.save(cliente);
    }

    @Test
    void deveCriarNovoEquipamento() {
        var requestDTO = criarEquipamentoCompletoDTO();

        given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
        .when()
                .post()
        .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .header("Location", matchesPattern(".*/equipamento/\\d+"))
                .body("marca", equalTo("Pioneer"))
                .body("modelo", equalTo("CDJ-231312"))
                .body("numeroSerie", equalTo("C123112312"))
                .body("id", notNullValue());
    }

    @Test
    void deveListarTodos() {
        equipamentoRepository.saveAndFlush(toEntity(criarEquipamentoCompletoDTO()));

        given()
        .when()
                .get()
        .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].marca", equalTo("Pioneer"));
    }

    @Test
    void deveBuscarPorId() {
        Equipamento equipamentoSalvo = equipamentoRepository.saveAndFlush(toEntity(criarEquipamentoCompletoDTO()));

        given()
        .when()
                .get("/{id}", equipamentoSalvo.getId())
        .then()
                .statusCode(200)
                .body("id", equalTo(equipamentoSalvo.getId().intValue()))
                .body("marca", equalTo("Pioneer"));
    }

    @Test
    void deveRetornarNotFoundAoBuscarInexistente()
    {
        given()
        .when()
                .get("/999")
        .then()
                .statusCode(404);
    }

    @Test
    void deveDeletarEquipamento() {
        Equipamento equipamentoSalvo = equipamentoRepository.saveAndFlush(toEntity(criarEquipamentoCompletoDTO()));

        given()
        .when()
                .delete("/{id}", equipamentoSalvo.getId())
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

    private EquipamentoRequestDTO criarEquipamentoCompletoDTO() {
        Long tipoId = tipoEquipamentoRepository.findAll().get(0).getId();
        Long clienteId = clienteRepository.findAll().get(0).getId();

        return new EquipamentoRequestDTO(
                tipoId,
                "Pioneer",
                "CDJ-231312",
                "C123112312",
                clienteId
        );
    }

    private Equipamento toEntity(EquipamentoRequestDTO dto) {
        Equipamento equipamento = new Equipamento();
        equipamento.setMarca(dto.marca());
        equipamento.setModelo(dto.modelo());
        equipamento.setNumeroSerie(dto.numeroSerie());

        equipamento.setTipoEquipamento(
                tipoEquipamentoRepository.findById(dto.tipoEquipamentoId()).get()
        );
        equipamento.setCliente(
                clienteRepository.findById(dto.clienteId()).get()
        );

        return equipamento;
    }
}

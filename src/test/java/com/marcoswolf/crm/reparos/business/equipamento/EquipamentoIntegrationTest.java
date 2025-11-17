package com.marcoswolf.crm.reparos.business.equipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ClienteRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.TipoEquipamentoRepository;
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
public class EquipamentoIntegrationTest {
    @Autowired private EquipamentoService equipamentoService;

    @Autowired private EquipamentoRepository equipamentoRepository;
    @Autowired private TipoEquipamentoRepository tipoEquipamentoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ReparoRepository reparoRepository;

    @AfterEach
    void limparBanco() {
        equipamentoRepository.deleteAll();
    }

    @Test
    void deveListarTodosOsEquipamentos() {
        Equipamento equipamento1 = criarEquipamento(
                "Behringer", "NSXSAD", "NS123123-551X",
                "Marcos", "Mesa de Som"
        );
        Equipamento equipamento2 = criarEquipamento(
                "Pioneer", "CDJ-212312", "CDJSASD12312xA",
                "Pietro", "Mesa de DJ"
        );

        equipamentoRepository.save(equipamento1);
        equipamentoRepository.save(equipamento2);

        var resultado = equipamentoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(c -> c.getMarca().equals("Behringer")));
        assertTrue(resultado.stream().anyMatch(c -> c.getMarca().equals("Pioneer")));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremEquipamentos() {
        var resultado = equipamentoService.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveSalvarUmNovoEquipamento() {
        Equipamento equipamento = criarEquipamento(
                "Sone", "PolyStation", "PS323412",
                "Marcelo", "Console"
        );
        equipamentoService.salvar(equipamento);

        var equipamentoSalvo = equipamentoRepository.findAll().get(0);
        assertNotNull(equipamento.getId());
        assertEquals("Sone", equipamentoSalvo.getMarca());
        assertEquals("PolyStation", equipamentoSalvo.getModelo());
        assertEquals("PS323412", equipamentoSalvo.getNumeroSerie());
        assertEquals("Marcelo", equipamentoSalvo.getCliente().getNome());
        assertEquals("Console", equipamentoSalvo.getTipoEquipamento().getNome());
    }

    @Test
    void deveAtualizarUmEquipamento() {
        Equipamento equipamento = criarEquipamento(
                "Boss", "GT-2000", "BSSGT43242352XZS",
                "André", "Pedaleira"
        );
        equipamentoService.salvar(equipamento);

        Cliente cliente = equipamento.getCliente();
        cliente.setNome("Miguel");
        clienteRepository.save(cliente);

        TipoEquipamento tipoEquipamento = equipamento.getTipoEquipamento();
        tipoEquipamento.setNome("Pedal");
        tipoEquipamentoRepository.save(tipoEquipamento);

        equipamento.setMarca("Znx");
        equipamento.setModelo("Overdrive");
        equipamento.setNumeroSerie("OVRD-12312312");
        equipamento.setCliente(cliente);
        equipamento.setTipoEquipamento(tipoEquipamento);

        equipamentoService.salvar(equipamento);

        var equipamentoAtualizado = equipamentoRepository.findById(equipamento.getId()).orElseThrow();

        assertNotNull(equipamento);
        assertEquals("Znx", equipamentoAtualizado.getMarca());
        assertEquals("Overdrive", equipamentoAtualizado.getModelo());
        assertEquals("OVRD-12312312", equipamentoAtualizado.getNumeroSerie());
        assertEquals("Miguel", equipamentoAtualizado.getCliente().getNome());
        assertEquals("Pedal", equipamentoAtualizado.getTipoEquipamento().getNome());
    }

    @Test
    void deveDeletarUmEquipamento() {
        Equipamento equipamento = criarEquipamento(
                "ERQ", "ZX-2321", "ERQZX-1231312",
                "Roberto", "Microondas"
        );
        equipamentoService.salvar(equipamento);

        Long equipamentoId = equipamento.getId();

        equipamentoService.deletar(equipamentoId);

        assertFalse(equipamentoRepository.existsById(equipamentoId));
    }

    @Test
    void deveLancarExcecaoAoExcluirUmEquipamentoInexistente() {
        var exception = assertThrows(RuntimeException.class,
                () -> equipamentoService.deletar(999L)
        );

        assertEquals("Equipamento não encontrado.", exception.getMessage());
    }

    private Equipamento criarEquipamento(
        String marca, String modelo, String numeroSerie,
        String clienteNome, String tipoEquipamentoNome
    ) {
        TipoEquipamento tipoAux = new TipoEquipamento();
        tipoAux.setNome(tipoEquipamentoNome);
        tipoEquipamentoRepository.save(tipoAux);

        Cliente clienteAux = new Cliente();
        clienteAux.setNome(clienteNome);
        clienteRepository.save(clienteAux);

        Equipamento equipamento = new Equipamento();
        equipamento.setMarca(marca);
        equipamento.setModelo(modelo);
        equipamento.setNumeroSerie(numeroSerie);
        equipamento.setCliente(clienteAux);
        equipamento.setTipoEquipamento(tipoAux);

        return equipamento;
    }
}

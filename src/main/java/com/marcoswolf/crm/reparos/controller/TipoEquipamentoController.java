package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.tipoEquipamento.TipoEquipamentoService;
import com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro.TipoEquipamentoFiltro;
import com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro.TipoEquipamentoFiltroService;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-equipamento")
@RequiredArgsConstructor

public class TipoEquipamentoController {
    private final TipoEquipamentoService tipoEquipamentoService;
    private final TipoEquipamentoFiltroService tipoEquipamentoFiltroService;

    // Create
    @PostMapping
    public ResponseEntity<TipoEquipamento> salvarTipoEquipamento(@RequestBody TipoEquipamento tipoEquipamento) {
        tipoEquipamentoService.salvarTipoEquipamento(tipoEquipamento);
        return ResponseEntity.ok(tipoEquipamento);
    }

    // Read
    @GetMapping("/filtrar")
    public ResponseEntity<List<TipoEquipamento>> filtrarTipoEquipamento(TipoEquipamentoFiltro filtro) {
        List<TipoEquipamento> tipoEquipamentos = tipoEquipamentoFiltroService.aplicarFiltros(filtro);
        return ResponseEntity.ok(tipoEquipamentos);
    }

    // Update
    @PutMapping
    public ResponseEntity<TipoEquipamento> atualizarTipoEquipamento(@RequestParam Long id, @RequestBody TipoEquipamento tipoEquipamento) {
        TipoEquipamento novoTipoEquipamento = tipoEquipamentoService.atualizarTipoEquipamento(id, tipoEquipamento);
        return ResponseEntity.ok(novoTipoEquipamento);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<String> deletarTipoEquipamento(@RequestParam Long id) {
        try {
            tipoEquipamentoService.deletarTipoEquipamento(id);
            return ResponseEntity.ok("Tipo de equipamento deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
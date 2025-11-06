package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.TipoEquipamentoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-equipamento")
@RequiredArgsConstructor

public class TipoEquipamentoController {
    private final TipoEquipamentoService service;

    // Create
    @PostMapping
    public ResponseEntity<TipoEquipamento> salvarTipoEquipamento(@RequestBody TipoEquipamento tipoEquipamento) {
        service.salvarTipoEquipamento(tipoEquipamento);
        return ResponseEntity.ok(tipoEquipamento);
    }

    // Read
    @GetMapping
    public ResponseEntity<List<TipoEquipamento>> buscarPorNome(@RequestParam String nome) {
        List<TipoEquipamento> equipamentos = service.buscarPorNome(nome);
        return ResponseEntity.ok(equipamentos);
    }

    // Update
    @PutMapping
    public ResponseEntity<TipoEquipamento> atualizarTipoEquipamento(@RequestParam Integer id, @RequestBody TipoEquipamento tipoEquipamento) {
        TipoEquipamento novoTipoEquipamento = service.atualizarTipoEquipamento(id, tipoEquipamento);
        return ResponseEntity.ok(novoTipoEquipamento);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<String> deletarTipoEquipamento(@RequestParam Integer id) {
        try {
            service.deletarTipoEquipamento(id);
            return ResponseEntity.ok("Tipo de equipamento deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
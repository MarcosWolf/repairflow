package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.EquipamentoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamento")
@RequiredArgsConstructor

public class EquipamentoController {
    private final EquipamentoService service;

    // Create
    @PostMapping
    public ResponseEntity<Equipamento> salvarEquipamento(@RequestBody Equipamento equipamento) {
        service.salvarEquipamento(equipamento);
        return ResponseEntity.ok(equipamento);
    }

    // Read
    @GetMapping
    public ResponseEntity<List<Equipamento>> buscarPorNumeroSerie(@RequestParam String numeroSerie) {
        List<Equipamento> equipamentos = service.buscarPorNumeroSerie(numeroSerie);
        return ResponseEntity.ok(equipamentos);
    }

    // Update
    @PutMapping
    public ResponseEntity<Equipamento> atualizarEquipamento(@RequestParam Long id, @RequestBody Equipamento equipamento) {
        Equipamento novoEquipamento = service.atualizarEquipamento(id, equipamento);
        return ResponseEntity.ok(novoEquipamento);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<String> deletarEquipamento(@RequestParam Long id) {
        try {
            service.deletarEquipamento(id);
            return ResponseEntity.ok("Equipamento deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
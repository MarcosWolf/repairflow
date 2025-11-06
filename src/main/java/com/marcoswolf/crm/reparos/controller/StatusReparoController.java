package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.StatusReparoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.StatusReparo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status-reparo")
@RequiredArgsConstructor

public class StatusReparoController {
    private final StatusReparoService service;

    // Create
    @PostMapping
    public ResponseEntity<StatusReparo> salvarStatusReparo(@RequestBody StatusReparo statusReparo) {
        service.salvarStatusReparo(statusReparo);
        return ResponseEntity.ok(statusReparo);
    }

    // Read
    @GetMapping
    public ResponseEntity<List<StatusReparo>> buscarStatusReparo(@RequestParam String nome) {
        List<StatusReparo> statusReparos = service.buscarPorNome(nome);
        return ResponseEntity.ok(statusReparos);
    }

    // Update
    @PutMapping
    public ResponseEntity<StatusReparo> atualizarStatusReparo(@RequestParam Integer id, @RequestBody StatusReparo statusReparo) {
        StatusReparo novoStatusReparo = service.atualizarStatusReparo(id, statusReparo);
        return ResponseEntity.ok(novoStatusReparo);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<String> deletarStatusReparo(@RequestParam Integer id) {
        try {
            service.deletarStatusReparo(id);
            return ResponseEntity.ok("Status de reparo deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
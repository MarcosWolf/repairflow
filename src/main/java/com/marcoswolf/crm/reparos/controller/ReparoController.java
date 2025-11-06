package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.ReparoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reparo")
@RequiredArgsConstructor

public class ReparoController {
    private final ReparoService service;

    // Create
    @PostMapping
    public ResponseEntity<Reparo> salvarReparo(@RequestBody Reparo reparo) {
        service.salvarReparo(reparo);
        return ResponseEntity.ok(reparo);
    }

    // Read
    @GetMapping
    public ResponseEntity<List<Reparo>> buscarPorStatus(@RequestParam String status) {
        List<Reparo> reparos = service.buscarPorStatus(status);
        return ResponseEntity.ok(reparos);
    }

    // Update
    @PutMapping
    public ResponseEntity<Reparo> atualizarReparo(@RequestParam Long id, @RequestBody Reparo reparo) {
        Reparo novoReparo = service.atualizarReparo(id, reparo);
        return ResponseEntity.ok(novoReparo);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<String> deletarReparo(@RequestParam Long id) {
        try {
            service.deletarReparo(id);
            return ResponseEntity.ok("Reparo deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
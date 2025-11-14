package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.reparo.ReparoService;
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

    @PostMapping
    public ResponseEntity<Reparo> salvar(@RequestBody Reparo reparo) {
        service.salvar(reparo);
        return ResponseEntity.ok(reparo);
    }

    @GetMapping
    public ResponseEntity<List<Reparo>> listarTodos() {
        List<Reparo> reparos = service.listarTodos();
        return ResponseEntity.ok(reparos);
    }
    
    @DeleteMapping
    public ResponseEntity<String> deletar(@RequestParam Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.ok("Reparo deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
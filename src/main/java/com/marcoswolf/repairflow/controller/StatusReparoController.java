package com.marcoswolf.repairflow.controller;

import com.marcoswolf.repairflow.business.statusReparo.StatusReparoService;
import com.marcoswolf.repairflow.infrastructure.entities.StatusReparo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/status-reparo")
@RequiredArgsConstructor
public class StatusReparoController {
    private final StatusReparoService service;

    @PostMapping
    public ResponseEntity<StatusReparo> salvar(@RequestBody StatusReparo statusReparo) {
        service.salvar(statusReparo);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(statusReparo.getId())
                .toUri();

        return ResponseEntity.created(location).body(statusReparo);
    }

    @GetMapping
    public ResponseEntity<List<StatusReparo>> listarTodos() {
        List<StatusReparo> statusReparos = service.listarTodos();
        return ResponseEntity.ok(statusReparos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusReparo> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
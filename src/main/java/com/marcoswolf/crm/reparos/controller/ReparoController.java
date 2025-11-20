package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.reparo.ReparoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reparo")
@RequiredArgsConstructor
public class ReparoController {
    private final ReparoService service;

    @PostMapping
    public ResponseEntity<Reparo> salvar(@RequestBody Reparo reparo) {
        service.salvar(reparo);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reparo.getId())
                .toUri();

        return ResponseEntity.created(location).body(reparo);
    }

    @GetMapping
    public ResponseEntity<List<Reparo>> listarTodos() {
        List<Reparo> reparos = service.listarTodos();
        return ResponseEntity.ok(reparos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reparo> buscarPorId(@PathVariable Long id) {
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
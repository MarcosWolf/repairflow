package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.equipamento.EquipamentoService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/equipamento")
@RequiredArgsConstructor
public class EquipamentoController {
    private final EquipamentoService service;

    @PostMapping
    public ResponseEntity<Equipamento> salvar(@RequestBody Equipamento equipamento) {
        service.salvar(equipamento);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(equipamento.getId())
                .toUri();

        return ResponseEntity.created(location).body(equipamento);
    }

    @GetMapping
    public ResponseEntity<List<Equipamento>> listarTodos() {
        List<Equipamento> equipamentos = service.listarTodos();
        return ResponseEntity.ok(equipamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipamento> buscarPorId(@PathVariable Long id) {
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
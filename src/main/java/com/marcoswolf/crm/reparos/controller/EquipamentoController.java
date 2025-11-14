package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.equipamento.EquipamentoService;
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

    @PostMapping
    public ResponseEntity<Equipamento> salvar(@RequestBody Equipamento equipamento) {
        service.salvar(equipamento);
        return ResponseEntity.ok(equipamento);
    }

    @GetMapping
    public ResponseEntity<List<Equipamento>> listarTodos() {
        List<Equipamento> equipamentos = service.listarTodos();
        return ResponseEntity.ok(equipamentos);
    }

    @DeleteMapping
    public ResponseEntity<String> deletar(@RequestParam Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.ok("Equipamento deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
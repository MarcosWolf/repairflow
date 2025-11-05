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
    private final EquipamentoService equipamentoService;

    @PostMapping
    public ResponseEntity<Equipamento> salvarEquipamento(@RequestBody Equipamento equipamento) {
        equipamentoService.salvarEquipamento(equipamento);
        return ResponseEntity.ok(equipamento);
    }

    @GetMapping
    public ResponseEntity<List<Equipamento>> buscarPorNumeroSerie(@RequestParam String numeroSerie) {
        List<Equipamento> equipamentos = equipamentoService.buscarPorNumeroSerie(numeroSerie);
        return ResponseEntity.ok(equipamentos);
    }
}

package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.tipoEquipamento.TipoEquipamentoService;
import com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro.TipoEquipamentoFiltro;
import com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro.TipoEquipamentoFiltroService;
import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-equipamento")
@RequiredArgsConstructor

public class TipoEquipamentoController {
    private final TipoEquipamentoService tipoEquipamentoService;
    private final TipoEquipamentoFiltroService tipoEquipamentoFiltroService;

    @PostMapping
    public ResponseEntity<TipoEquipamento> salvar(@RequestBody TipoEquipamento tipoEquipamento) {
        tipoEquipamentoService.salvar(tipoEquipamento);
        return ResponseEntity.ok(tipoEquipamento);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<TipoEquipamento>> filtrar(TipoEquipamentoFiltro filtro) {
        List<TipoEquipamento> tipoEquipamentos = tipoEquipamentoFiltroService.aplicarFiltros(filtro);
        return ResponseEntity.ok(tipoEquipamentos);
    }

    @DeleteMapping
    public ResponseEntity<String> deletar(@RequestParam Long id) {
        try {
            tipoEquipamentoService.deletar(id);
            return ResponseEntity.ok("Tipo de equipamento deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
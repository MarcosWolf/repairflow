package com.marcoswolf.repairflow.controller;

import com.marcoswolf.repairflow.business.tipoEquipamento.TipoEquipamentoService;
import com.marcoswolf.repairflow.business.tipoEquipamento.filtro.TipoEquipamentoFiltro;
import com.marcoswolf.repairflow.business.tipoEquipamento.filtro.TipoEquipamentoFiltroService;
import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tipo-equipamento")
@RequiredArgsConstructor
public class TipoEquipamentoController {
    private final TipoEquipamentoService tipoEquipamentoService;
    private final TipoEquipamentoFiltroService tipoEquipamentoFiltroService;

    @PostMapping
    public ResponseEntity<TipoEquipamento> salvar(@RequestBody TipoEquipamento tipoEquipamento) {
        tipoEquipamentoService.salvar(tipoEquipamento);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tipoEquipamento.getId())
                .toUri();

        return ResponseEntity.created(location).body(tipoEquipamento);
    }

    @GetMapping
    public ResponseEntity<List<TipoEquipamento>> listarTodos() {
        List<TipoEquipamento> tipoEquipamentos = tipoEquipamentoService.listarTodos();
        return ResponseEntity.ok(tipoEquipamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEquipamento> buscarPorId(@PathVariable Long id) {
        return tipoEquipamentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<TipoEquipamento>> filtrar(TipoEquipamentoFiltro filtro) {
        List<TipoEquipamento> tipoEquipamentos = tipoEquipamentoFiltroService.aplicarFiltros(filtro);
        return ResponseEntity.ok(tipoEquipamentos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            tipoEquipamentoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.cliente.ClienteService;
import com.marcoswolf.crm.reparos.business.cliente.ClienteConsultaService;
import com.marcoswolf.crm.reparos.business.cliente.filtro.ClienteFiltro;
import com.marcoswolf.crm.reparos.business.cliente.filtro.ClienteFiltroService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;
    private final ClienteConsultaService clienteConsultaService;
    private final ClienteFiltroService clienteFiltroService;

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
        clienteService.salvar(cliente);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cliente.getId())
                .toUri();

        return ResponseEntity.created(location).body(cliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteConsultaService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<Cliente>> filtrarClientes(ClienteFiltro filtro) {
        List<Cliente> clientes = clienteFiltroService.aplicarFiltros(filtro);
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            clienteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
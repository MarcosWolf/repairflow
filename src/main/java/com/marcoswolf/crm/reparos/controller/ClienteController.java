package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.cliente.ClienteService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor

public class ClienteController {
    private final ClienteService service;

    // Create
    @PostMapping
    public ResponseEntity<Cliente> salvarCliente(@RequestBody Cliente cliente) {
        service.salvarCliente(cliente);
        return ResponseEntity.ok(cliente);
    }

    // Read
    @GetMapping
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
        List<Cliente> clientes = service.buscarPorNome(nome);
        return ResponseEntity.ok(clientes);
    }

    // Update
    @PutMapping
    public ResponseEntity<Cliente> atualizarCliente(@RequestParam Long id, @RequestBody Cliente cliente) {
        Cliente novoCliente = service.atualizarCliente(id, cliente);
        return ResponseEntity.ok(novoCliente);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<String> deletarCliente(@RequestParam Long id) {
        try {
            service.deletarCliente(id);
            return ResponseEntity.ok("Cliente deletado com sucesso.");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
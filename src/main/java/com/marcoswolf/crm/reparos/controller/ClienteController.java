package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.ClienteService;
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

    @PostMapping
    public ResponseEntity<Cliente> salvarCliente(@RequestBody Cliente cliente) {
        service.salvarCliente(cliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
        List<Cliente> clientes = service.buscarPorNome(nome);
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping
    public ResponseEntity<String> deletarClientePorId(@RequestParam Integer id) {
        try {
            service.deletarClientePorId(id);
            return ResponseEntity.ok("Cliente deletado com sucesso.");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

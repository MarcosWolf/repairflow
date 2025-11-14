package com.marcoswolf.crm.reparos.controller;

import com.marcoswolf.crm.reparos.business.cliente.ClienteService;
import com.marcoswolf.crm.reparos.business.cliente.ClienteConsultaService;
import com.marcoswolf.crm.reparos.business.cliente.filtro.ClienteFiltro;
import com.marcoswolf.crm.reparos.business.cliente.filtro.ClienteFiltroService;
import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteConsultaService clienteConsultaService;
    private final ClienteFiltroService clienteFiltroService;

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
        clienteService.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteConsultaService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<Cliente>> filtrarClientes(ClienteFiltro filtro) {
        List<Cliente> clientes = clienteFiltroService.aplicarFiltros(filtro);
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping
    public ResponseEntity<String> deletar(@RequestParam Long id) {
        try {
            clienteService.deletar(id);
            return ResponseEntity.ok("Cliente deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
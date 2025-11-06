package com.marcoswolf.crm.reparos.infrastructure.repositories;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Cliente
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}

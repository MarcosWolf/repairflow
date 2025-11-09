package com.marcoswolf.crm.reparos.infrastructure.repositories;

import com.marcoswolf.crm.reparos.infrastructure.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Cliente
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    boolean existsByTelefone(String telefone);

    @Query("SELECT COUNT(t) > 0 FROM TipoEquipamento t WHERE LOWER(t.nome) = LOWER(:nome)")
    boolean existsByEmail(String email);
}

package com.marcoswolf.crm.reparos.infrastructure.repositories;

import com.marcoswolf.crm.reparos.infrastructure.entities.StatusReparo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusReparoRepository extends JpaRepository<StatusReparo, Integer> {
    // StatusReparo
    List<StatusReparo> findByNomeContainingIgnoreCase(String nome);
}

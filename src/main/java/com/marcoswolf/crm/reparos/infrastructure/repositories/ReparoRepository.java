package com.marcoswolf.crm.reparos.infrastructure.repositories;

import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReparoRepository extends JpaRepository<Reparo, Long> {
    // Reparo
    List<Reparo> findByStatus_NomeContainingIgnoreCase(String nomeStatus);
    // Cliente
    List<Reparo> findByEquipamento_Cliente_Id(Long id);
    // Equipamento
    List<Reparo> findByEquipamento_Id(Long id);
    // StatusReparo
    List<Reparo> findByStatus_Id(Long id);
}

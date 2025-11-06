package com.marcoswolf.crm.reparos.infrastructure.repositories;

import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReparoRepository extends JpaRepository<Reparo, Integer> {
    List<Reparo> findByStatus_NomeContainingIgnoreCase(String nomeStatus);

    List<Reparo> findByEquipamento_Cliente_Id(Integer id);

    List<Reparo> findByEquipamento_Id(Integer id);
}

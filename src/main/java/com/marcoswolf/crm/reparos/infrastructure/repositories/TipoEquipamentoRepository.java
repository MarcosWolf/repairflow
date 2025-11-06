package com.marcoswolf.crm.reparos.infrastructure.repositories;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoEquipamentoRepository extends JpaRepository<TipoEquipamento, Integer> {
    // TipoEquipamento
    List<TipoEquipamento> findByNomeContainingIgnoreCase(String nome);
}

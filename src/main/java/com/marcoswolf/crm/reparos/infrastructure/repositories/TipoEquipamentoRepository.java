package com.marcoswolf.crm.reparos.infrastructure.repositories;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoEquipamentoRepository extends JpaRepository<TipoEquipamento, Long> {
    // TipoEquipamento
    List<TipoEquipamento> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT COUNT(t) > 0 FROM TipoEquipamento t WHERE LOWER(t.nome) = LOWER(:nome)")
    boolean existsByNome(String nome);
}

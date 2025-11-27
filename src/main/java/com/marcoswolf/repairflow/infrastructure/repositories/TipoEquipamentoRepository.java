package com.marcoswolf.repairflow.infrastructure.repositories;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TipoEquipamentoRepository extends JpaRepository<TipoEquipamento, Long> {
    @Query("""
        SELECT COUNT(c) > 0 
        FROM TipoEquipamento c 
        WHERE LOWER(c.nome) = LOWER(:nome)
        AND (:id IS NULL OR c.id <> :id)
    """)
    boolean existsByNomeAndNotId(@Param("nome") String nome, @Param("id") Long id);
}

package com.marcoswolf.crm.reparos.infrastructure.repositories;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    // Cliente
    boolean existsByClienteId(Long id);

    // Tipo Equipamento
    boolean existsByTipoEquipamentoId(Long id);

    @Query("""
        SELECT COUNT(e)
        FROM Equipamento e
        WHERE e.tipoEquipamento.id = :tipoId
    """)
    Long countByTipoEquipamentoId(@Param("tipoId") Long tipoId);

    // Termo
    @Query("""
        SELECT e FROM Equipamento e
        WHERE LOWER(CONCAT(
            COALESCE(e.marca, ''), ' ',
            COALESCE(e.modelo, ''), ' ',
            COALESCE(e.numeroSerie, '')
        )) LIKE LOWER(CONCAT('%', :termo, '%'))
    """)
    List<Equipamento> buscarPorTermo(@Param("termo") String termo);

    // Reparo
    List<Equipamento> findByCliente_Id(Long clienteId);
}

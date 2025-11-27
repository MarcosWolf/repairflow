package com.marcoswolf.repairflow.infrastructure.repositories;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Cliente
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    boolean existsByTelefone(String telefone);
    boolean existsByEmail(String email);

    @Query("""
        SELECT COUNT(c) > 0 
        FROM Cliente c 
        WHERE LOWER(c.telefone) = LOWER(:telefone)
        AND (:id IS NULL OR c.id <> :id)
    """)
    boolean existsByTelefoneAndNotId(@Param("telefone") String telefone, @Param("id") Long id);

    @Query("""
        SELECT COUNT(c) > 0 
        FROM Cliente c 
        WHERE LOWER(c.email) = LOWER(:email)
        AND (:id IS NULL OR c.id <> :id)
    """)
    boolean existsByEmailAndNotId(@Param("email") String email, @Param("id") Long id);

    // Tipo Equipamento
    @Query("""
    SELECT COUNT(DISTINCT e.cliente)
    FROM Equipamento e
    WHERE e.tipoEquipamento.id = :tipoId
""")
    Long countByTipoEquipamentoId(@Param("tipoId") Long tipoId);
}

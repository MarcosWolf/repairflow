package com.marcoswolf.crm.reparos.infrastructure.repositories;

import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReparoRepository extends JpaRepository<Reparo, Long> {
    // Reparo
    List<Reparo> findByStatus_NomeContainingIgnoreCase(String nomeStatus);
    // Cliente
    List<Reparo> findByEquipamento_Cliente_Id(Long id);
    boolean existsByEquipamento_Cliente_IdAndPagamento_PagoFalse(Long clienteId);
    boolean existsByEquipamento_Cliente_IdAndConcluidoFalse(Long clientId);
    boolean existsByEquipamento_Cliente_IdAndDataEntradaAfter(Long clientId, LocalDate dataEntrada);
    // Equipamento
    List<Reparo> findByEquipamento_Id(Long id);
    boolean existsByEquipamento_Cliente_Id(Long clienteId);
    // StatusReparo
    List<Reparo> findByStatus_Id(Long id);
}

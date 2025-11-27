package com.marcoswolf.repairflow.infrastructure.repositories;

import com.marcoswolf.repairflow.infrastructure.entities.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
}

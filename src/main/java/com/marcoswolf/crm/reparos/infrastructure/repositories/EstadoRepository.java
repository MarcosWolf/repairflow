package com.marcoswolf.crm.reparos.infrastructure.repositories;

import com.marcoswolf.crm.reparos.infrastructure.entities.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
}

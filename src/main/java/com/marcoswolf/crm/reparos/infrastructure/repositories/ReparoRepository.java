package com.marcoswolf.crm.reparos.infrastructure.repositories;

import com.marcoswolf.crm.reparos.infrastructure.entities.Reparo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReparoRepository extends JpaRepository<Reparo, Integer> {

}

package com.vet.mwoof.proyecto_veterinaria.repository;

import com.vet.mwoof.proyecto_veterinaria.entity.BloqueosFechaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BloqueosFechaRepository extends JpaRepository<BloqueosFechaEntity, Integer> {
   
    Optional<BloqueosFechaEntity> findByFecha(LocalDate fecha);
    
    List<BloqueosFechaEntity> findByBloqueadoTrue();
}

package com.vet.mwoof.proyecto_veterinaria.repository;

import com.vet.mwoof.proyecto_veterinaria.entity.FestivosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FestivosRepository extends JpaRepository<FestivosEntity, Integer> {
    
    Optional<FestivosEntity> findByFecha(LocalDate fecha);
    
    List<FestivosEntity> findByBloqueadoTrue();
    
}

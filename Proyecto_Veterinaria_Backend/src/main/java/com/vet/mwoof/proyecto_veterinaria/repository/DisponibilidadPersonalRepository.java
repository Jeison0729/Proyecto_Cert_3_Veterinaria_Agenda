package com.vet.mwoof.proyecto_veterinaria.repository;

import com.vet.mwoof.proyecto_veterinaria.entity.DisponibilidadPersonalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DisponibilidadPersonalRepository extends JpaRepository<DisponibilidadPersonalEntity, Long> {
    
    List<DisponibilidadPersonalEntity> findByPersonalIdAndFechaBetween(Long personalId, LocalDate fechaInicio, LocalDate fechaFin);
    
    List<DisponibilidadPersonalEntity> findByPersonalId(Long personalId);
    
    boolean existsByPersonalIdAndFechaAndHoraInicioLessThanAndHoraFinGreaterThan(Long personalId, LocalDate fecha, java.time.LocalTime horaFin, java.time.LocalTime horaInicio);
}

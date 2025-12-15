package com.vet.mwoof.proyecto_veterinaria.repository;

import com.vet.mwoof.proyecto_veterinaria.entity.AgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {
    List<AgendaEntity> findByFecha(LocalDate fecha);
    
    List<AgendaEntity> findByFechaBetween(LocalDate inicio, LocalDate fin);
    
    List<AgendaEntity> findByClienteId(Long idCliente);
    
    List<AgendaEntity> findByMascotaId(Long idMascota);
    
    List<AgendaEntity> findByFechaBetweenAndPersonalId(LocalDate inicio, LocalDate fin, Long personalId);
    
    
}

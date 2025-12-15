package com.vet.mwoof.proyecto_veterinaria.repository;

import com.vet.mwoof.proyecto_veterinaria.entity.HorarioAtencionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioAtencionRepository extends JpaRepository<HorarioAtencionEntity, Integer> {
    HorarioAtencionEntity findByDiaSemana(Integer diaSemana);
    
    List<HorarioAtencionEntity> findByActivoTrue();
    
}

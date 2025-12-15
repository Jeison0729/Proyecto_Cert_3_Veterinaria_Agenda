package com.vet.mwoof.proyecto_veterinaria.repository;

import com.vet.mwoof.proyecto_veterinaria.entity.RecordatorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecordatorioRepository extends JpaRepository<RecordatorioEntity, Long> {
    
    List<RecordatorioEntity> findByEnviadoFalseAndFechaProgramadaBefore(LocalDateTime fecha);
    
    List<RecordatorioEntity> findByAgendaId(Long idAgenda);
    
    List<RecordatorioEntity> findByEnviadoFalse();
    
}

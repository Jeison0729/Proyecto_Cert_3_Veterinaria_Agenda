package com.vet.mwoof.proyecto_veterinaria.repository;

import com.vet.mwoof.proyecto_veterinaria.entity.AgendaPagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendaPagoRepository extends JpaRepository<AgendaPagoEntity, Long> {
    // Si necesitas pagos por agenda:
    List<AgendaPagoEntity> findByAgendaId(Long agendaId);
    
    // Si necesitas pagos por fecha:
    List<AgendaPagoEntity> findByFechaPagoBetween(java.time.LocalDateTime inicio, java.time.LocalDateTime fin);
}

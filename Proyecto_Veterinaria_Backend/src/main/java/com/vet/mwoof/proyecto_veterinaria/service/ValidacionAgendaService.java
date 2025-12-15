package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.response.DisponibilidadDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ValidacionConflictoDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ValidacionAgendaService {
    ValidacionConflictoDTO validarConflictosHorario(
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            List<Long> idsPersonal
    );
    
    Boolean esHabilDia(LocalDate fecha);
    
    Boolean isFestivo(LocalDate fecha);
    
    Boolean isBloqueada(LocalDate fecha);
    
    Boolean esHoraValidaAtencion(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin);
    
    Boolean esPersonalDisponible(Long idPersonal, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin);
    
    
    DisponibilidadDTO obtenerHorasDisponibles(Long idPersonal, LocalDate fecha);
}

package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.dtos.response.*;
import com.vet.mwoof.proyecto_veterinaria.entity.*;
import com.vet.mwoof.proyecto_veterinaria.repository.*;
import com.vet.mwoof.proyecto_veterinaria.service.ValidacionAgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ValidacionAgendaServiceImpl implements ValidacionAgendaService {
    
    private final HorarioAtencionRepository horarioAtencionRepository;
    private final FestivosRepository festivosRepository;
    private final BloqueosFechaRepository bloqueosFechaRepository;
    private final DisponibilidadPersonalRepository disponibilidadRepository;
    private final AgendaRepository agendaRepository;
    
    private static final Duration SLOT_DURATION = Duration.ofMinutes(30);
    
    @Override
    public ValidacionConflictoDTO validarConflictosHorario(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, List<Long> idsPersonal) {
        ValidacionConflictoDTO resultado = new ValidacionConflictoDTO();
        List<AgendaSimpleDTO> conflictos = new ArrayList<>();
        
        if (!esHabilDia(fecha)) {
            resultado.setHayConflicto(true);
            resultado.setMensaje("No es día hábil (Martes a Sábado)");
            return resultado;
        }
        if (isFestivo(fecha)) {
            resultado.setHayConflicto(true);
            resultado.setMensaje("Es día festivo");
            return resultado;
        }
        if (isBloqueada(fecha)) {
            resultado.setHayConflicto(true);
            resultado.setMensaje("Fecha bloqueada por administrador");
            return resultado;
        }
        if (!esHoraValidaAtencion(fecha, horaInicio, horaFin)) {
            resultado.setHayConflicto(true);
            resultado.setMensaje("Fuera del horario de atención");
            return resultado;
        }
        
        List<AgendaEntity> citasDelDia = agendaRepository.findByFecha(fecha);
        for (AgendaEntity cita : citasDelDia) {
            if (!"CANCELADA".equals(cita.getEstado().getNombre())) {
                LocalTime finCita = cita.getHora().plusMinutes(cita.getDuracionEstimadaMin());
                if (horariosSeSuperponen(horaInicio, horaFin, cita.getHora(), finCita)) {
                    conflictos.add(AgendaSimpleDTO.builder()
                            .id(cita.getId())
                            .codigo(cita.getCodigo())
                            .fecha(cita.getFecha())
                            .hora(cita.getHora())
                            .duracionMin(cita.getDuracionEstimadaMin())
                            .mascota(cita.getMascota().getNombre())
                            .cliente(cita.getCliente().getNombres() + " " + cita.getCliente().getApellidos())
                            .build());
                }
            }
        }
        
        for (Long idPersonal : idsPersonal) {
            if (!esPersonalDisponible(idPersonal, fecha, horaInicio, horaFin)) {
                resultado.setHayConflicto(true);
                resultado.setMensaje("El personal no está disponible en este horario");
                resultado.setCitasConflictivas(conflictos);
                return resultado;
            }
        }
        
        if (!conflictos.isEmpty()) {
            resultado.setHayConflicto(true);
            resultado.setMensaje("Hay " + conflictos.size() + " cita(s) que se superponen");
            resultado.setCitasConflictivas(conflictos);
        } else {
            resultado.setHayConflicto(false);
            resultado.setMensaje("Horario disponible");
        }
        return resultado;
    }
    
    @Override
    public Boolean esHabilDia(LocalDate fecha) {
        DayOfWeek dia = fecha.getDayOfWeek();
        return dia.getValue() >= DayOfWeek.TUESDAY.getValue() && dia.getValue() <= DayOfWeek.SATURDAY.getValue();
    }
    
    @Override
    public Boolean isFestivo(LocalDate fecha) {
        return festivosRepository.findByFecha(fecha)
                .map(FestivosEntity::getBloqueado)
                .orElse(false);
    }
    
    @Override
    public Boolean isBloqueada(LocalDate fecha) {
        return bloqueosFechaRepository.findByFecha(fecha)
                .map(BloqueosFechaEntity::getBloqueado)
                .orElse(false);
    }
    
    @Override
    public Boolean esHoraValidaAtencion(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        int diaSemana = fecha.getDayOfWeek().getValue();
        HorarioAtencionEntity horario = horarioAtencionRepository.findByDiaSemana(diaSemana);
        return horario != null && horario.getActivo()
                && !horaInicio.isBefore(horario.getHoraInicio())
                && !horaFin.isAfter(horario.getHoraFin());
    }
    
    @Override
    public Boolean esPersonalDisponible(Long idPersonal, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        return disponibilidadRepository.findByPersonalIdAndFechaBetween(idPersonal, fecha, fecha).stream()
                .anyMatch(d -> d.getActivo()
                        && !horaInicio.isBefore(d.getHoraInicio())
                        && !horaFin.isAfter(d.getHoraFin()));
    }
    
    @Override
    public DisponibilidadDTO obtenerHorasDisponibles(Long idPersonal, LocalDate fecha) {
        DisponibilidadDTO dto = new DisponibilidadDTO();
        dto.setFecha(fecha);
        
        Set<LocalTime> disponibles = new TreeSet<>();
        Set<LocalTime> ocupadas = new TreeSet<>();
        
        if (!esHabilDia(fecha) || isFestivo(fecha) || isBloqueada(fecha)) {
            dto.setHorasDisponibles(List.of());
            dto.setHorasOcupadas(List.of());
            return dto;
        }
        
        HorarioAtencionEntity horarioDia = horarioAtencionRepository.findByDiaSemana(fecha.getDayOfWeek().getValue());
        if (horarioDia == null || !horarioDia.getActivo()) {
            dto.setHorasDisponibles(List.of());
            return dto;
        }
        
        LocalTime inicioDia = horarioDia.getHoraInicio();
        LocalTime finDia = horarioDia.getHoraFin();
        
        List<DisponibilidadPersonalEntity> bloques = disponibilidadRepository
                .findByPersonalIdAndFechaBetween(idPersonal, fecha, fecha);
        
        List<AgendaEntity> citasDelDia = agendaRepository.findByFecha(fecha);
        
        // EL FOR QUE ARREGLA TODO EL DRAMA
        for (LocalTime currentSlot = inicioDia;
             !currentSlot.isAfter(finDia.minusMinutes(30));
             currentSlot = currentSlot.plusMinutes(30)) {
            
            final LocalTime slot = currentSlot;
            final LocalTime slotFin = currentSlot.plusMinutes(30);
            
            boolean enBloqueDisponible = bloques.stream()
                    .anyMatch(d -> d.getActivo()
                            && !slot.isBefore(d.getHoraInicio())
                            && !slotFin.isAfter(d.getHoraFin()));
            
            boolean hayCita = citasDelDia.stream()
                    .filter(a -> !"CANCELADA".equals(a.getEstado().getNombre()))
                    .filter(a -> a.getServicios().stream()
                            .anyMatch(s -> Objects.equals(s.getPersonal().getId(), idPersonal)))
                    .anyMatch(a -> {
                        final LocalTime finCita = a.getHora().plusMinutes(a.getDuracionEstimadaMin());
                        return !slotFin.isBefore(a.getHora()) && !finCita.isBefore(slot);
                    });
            
            if (enBloqueDisponible && !hayCita) {
                disponibles.add(slot);
            } else if (hayCita) {
                ocupadas.add(slot);
            }
        }
        
        dto.setHorasDisponibles(new ArrayList<>(disponibles));
        dto.setHorasOcupadas(new ArrayList<>(ocupadas));
        return dto;
    }
    
    private boolean horariosSeSuperponen(LocalTime i1, LocalTime f1, LocalTime i2, LocalTime f2) {
        return !f1.isBefore(i2) && !f2.isBefore(i1);
    }
}

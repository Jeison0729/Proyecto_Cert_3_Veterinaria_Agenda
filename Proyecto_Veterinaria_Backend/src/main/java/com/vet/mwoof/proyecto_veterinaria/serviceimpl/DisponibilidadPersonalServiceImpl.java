package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.DisponibilidadPersonalRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.DisponibilidadPersonalResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.DisponibilidadPersonalResumenDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.DisponibilidadPersonalEntity;
import com.vet.mwoof.proyecto_veterinaria.entity.IngresoServicioEntity;
import com.vet.mwoof.proyecto_veterinaria.entity.PersonalEntity;
import com.vet.mwoof.proyecto_veterinaria.mapper.DisponibilidadPersonalMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.DisponibilidadPersonalRepository;
import com.vet.mwoof.proyecto_veterinaria.repository.IngresoServicioRepository;
import com.vet.mwoof.proyecto_veterinaria.repository.PersonalRepository;
import com.vet.mwoof.proyecto_veterinaria.service.DisponibilidadPersonalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisponibilidadPersonalServiceImpl implements DisponibilidadPersonalService {
    
    private final DisponibilidadPersonalRepository disponibilidadRepository;
    private final PersonalRepository personalRepository;
    private final IngresoServicioRepository ingresoServicioRepository;  // ← FALTABA ESTE
    private final DisponibilidadPersonalMapper mapper;
    
    @Override
    @Transactional
    public DisponibilidadPersonalResponseDTO crearDisponibilidad(DisponibilidadPersonalRequestDTO dto) {
        PersonalEntity personal = personalRepository.findById(dto.getIdPersonal())
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));
        
        if(dto.getHoraInicio().isAfter(dto.getHoraFin())) {
            throw new IllegalArgumentException("Hora de inicio no puede ser mayor que hora de fin");
        }
        
        boolean solapada = disponibilidadRepository.existsByPersonalIdAndFechaAndHoraInicioLessThanAndHoraFinGreaterThan(
                dto.getIdPersonal(), dto.getFecha(), dto.getHoraFin(), dto.getHoraInicio());
        
        if(solapada) {
            throw new IllegalArgumentException("Ya existe un bloque de disponibilidad que se solapa en ese horario");
        }
        
        DisponibilidadPersonalEntity entity = mapper.toEntity(dto, personal);
        DisponibilidadPersonalEntity saved = disponibilidadRepository.save(entity);
        return mapper.toResponseDTO(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<DisponibilidadPersonalResponseDTO> obtenerDisponibilidadPeriodo(Long idPersonal, LocalDate fechaInicio, LocalDate fechaFin) {
        return disponibilidadRepository.findByPersonalIdAndFechaBetween(idPersonal, fechaInicio, fechaFin).stream()
                .map(mapper :: toResponseDTO)
                .toList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public DisponibilidadPersonalResumenDTO obtenerResumenPersonal(Long idPersonal) {
        PersonalEntity personal = personalRepository.findById(idPersonal)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));
        
        LocalDate hoy = LocalDate.now();
        LocalDate proximoMes = hoy.plusMonths(1);
        
        List<IngresoServicioEntity> servicios = ingresoServicioRepository.findAll().stream()
                .filter(s -> s.getPersonal().getId().equals(idPersonal)
                        && ! s.getAgenda().getFecha().isBefore(hoy)
                        && ! s.getAgenda().getFecha().isAfter(proximoMes))
                .toList();
        
        long completadas = servicios.stream()
                .filter(s -> "COMPLETADA".equals(s.getAgenda().getEstado().getNombre()))
                .count();
        
        long canceladas = servicios.stream()
                .filter(s -> "CANCELADA".equals(s.getAgenda().getEstado().getNombre()))
                .count();
        
        long programadas = servicios.size();
        Double ocupacion = programadas > 0 ? (completadas * 100.0 / programadas) : 0.0;
        
        String estado = programadas == 0 ? "SIN_CITAS" : ocupacion > 80 ? "SOBRECARGADO" : "DISPONIBLE";
        
        return DisponibilidadPersonalResumenDTO.builder()
                .idPersonal(idPersonal)
                .nombrePersonal(personal.getNombres() + " " + personal.getApellidos())
                .citasProgramadas((int) programadas)
                .citasCompletadas((int) completadas)
                .citasCanceladas((int) canceladas)
                .porcentajeOcupacion(ocupacion)
                .estado(estado)
                .build();
    }
    
    @Override
    @Transactional
    public DisponibilidadPersonalResponseDTO actualizarDisponibilidad(Long id, DisponibilidadPersonalRequestDTO dto) {
        DisponibilidadPersonalEntity entity = disponibilidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disponibilidad no encontrada"));
        
        if(dto.getHoraInicio() != null && dto.getHoraFin() != null) {
            if(dto.getHoraInicio().isAfter(dto.getHoraFin())) {
                throw new IllegalArgumentException("Hora de inicio no puede ser mayor que hora de fin");
            }
        }
        
        mapper.updateEntityFromRequest(dto, entity);
        DisponibilidadPersonalEntity updated = disponibilidadRepository.save(entity);
        return mapper.toResponseDTO(updated);
    }
    
    @Override
    @Transactional
    public void eliminarDisponibilidad(Long id) {
        if(! disponibilidadRepository.existsById(id)) {
            throw new RuntimeException("Disponibilidad no encontrada");
        }
        disponibilidadRepository.deleteById(id);
    }
}

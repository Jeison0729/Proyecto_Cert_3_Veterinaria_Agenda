package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.dtos.response.HorarioAtencionDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.HorarioAtencionEntity;
import com.vet.mwoof.proyecto_veterinaria.mapper.HorarioAtencionMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.HorarioAtencionRepository;
import com.vet.mwoof.proyecto_veterinaria.service.HorarioAtencionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorarioAtencionServiceImpl implements HorarioAtencionService {
    
    private final HorarioAtencionRepository horarioRepository;
    private final HorarioAtencionMapper horarioMapper;
    
    @Override
    @Transactional
    public HorarioAtencionDTO crearHorario(HorarioAtencionDTO dto) {
        if(dto.getDiaSemana() == null || dto.getDiaSemana() < 1 || dto.getDiaSemana() > 7) {
            throw new IllegalArgumentException("Día de semana inválido. Debe estar entre 1 y 7");
        }
        
        HorarioAtencionEntity existente = horarioRepository.findByDiaSemana(dto.getDiaSemana());
        if(existente != null) {
            throw new IllegalArgumentException("Ya existe horario para este día de semana");
        }
        
        HorarioAtencionEntity entity = horarioMapper.toEntity(dto);
        HorarioAtencionEntity saved = horarioRepository.save(entity);
        
        return horarioMapper.toDTO(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<HorarioAtencionDTO> listarHorariosActivos() {
        return horarioRepository.findByActivoTrue().stream()
                .map(horarioMapper :: toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public HorarioAtencionDTO obtenerHorarioPorDia(Integer diaSemana) {
        HorarioAtencionEntity entity = horarioRepository.findByDiaSemana(diaSemana);
        
        if(entity == null) {
            throw new RuntimeException("Horario no encontrado para el día: " + diaSemana);
        }
        
        return horarioMapper.toDTO(entity);
    }
    
    @Override
    @Transactional
    public HorarioAtencionDTO actualizarHorario(Integer diaSemana, HorarioAtencionDTO dto) {
        HorarioAtencionEntity entity = horarioRepository.findByDiaSemana(diaSemana);
        
        if(entity == null) {
            throw new RuntimeException("Horario no encontrado para el día: " + diaSemana);
        }
        
        if(dto.getHoraInicio() != null && dto.getHoraFin() != null) {
            if(dto.getHoraInicio().isAfter(dto.getHoraFin())) {
                throw new IllegalArgumentException("La hora de inicio no puede ser mayor a la hora de fin");
            }
            entity.setHoraInicio(dto.getHoraInicio());
            entity.setHoraFin(dto.getHoraFin());
        }
        
        if(dto.getActivo() != null) {
            entity.setActivo(dto.getActivo());
        }
        
        HorarioAtencionEntity updated = horarioRepository.save(entity);
        return horarioMapper.toDTO(updated);
    }
}

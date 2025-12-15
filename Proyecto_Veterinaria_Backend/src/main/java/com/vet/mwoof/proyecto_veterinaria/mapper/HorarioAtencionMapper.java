package com.vet.mwoof.proyecto_veterinaria.mapper;

import com.vet.mwoof.proyecto_veterinaria.dtos.response.HorarioAtencionDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.HorarioAtencionEntity;
import org.springframework.stereotype.Component;

@Component
public class HorarioAtencionMapper {
    
    public HorarioAtencionDTO toDTO(HorarioAtencionEntity entity) {
        if(entity == null) {
            return null;
        }
        
        return HorarioAtencionDTO.builder()
                .id(entity.getId())
                .diaSemana(entity.getDiaSemana())
                .horaInicio(entity.getHoraInicio())
                .horaFin(entity.getHoraFin())
                .activo(entity.getActivo())
                .build();
    }
    
    public HorarioAtencionEntity toEntity(HorarioAtencionDTO dto) {
        if(dto == null) {
            return null;
        }
        
        return HorarioAtencionEntity.builder()
                .id(dto.getId())
                .diaSemana(dto.getDiaSemana())
                .horaInicio(dto.getHoraInicio())
                .horaFin(dto.getHoraFin())
                .activo(dto.getActivo() != null ? dto.getActivo() : true)
                .build();
    }
    
    public HorarioAtencionEntity toEntityUpdate(HorarioAtencionDTO dto, HorarioAtencionEntity existing) {
        if(dto.getHoraInicio() != null) {
            existing.setHoraInicio(dto.getHoraInicio());
        }
        if(dto.getHoraFin() != null) {
            existing.setHoraFin(dto.getHoraFin());
        }
        if(dto.getActivo() != null) {
            existing.setActivo(dto.getActivo());
        }
        return existing;
    }
}

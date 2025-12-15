package com.vet.mwoof.proyecto_veterinaria.mapper;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.BloqueosFechaRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.BloqueosFechaResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.BloqueosFechaEntity;
import org.springframework.stereotype.Component;

@Component
public class BloqueosFechaMapper {
    
    public BloqueosFechaResponseDTO toResponseDTO(BloqueosFechaEntity entity) {
        if(entity == null) return null;
        return BloqueosFechaResponseDTO.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .descripcion(entity.getDescripcion())
                .bloqueado(entity.getBloqueado())
                .build();
    }
    
    public BloqueosFechaEntity toEntity(BloqueosFechaRequestDTO dto) {
        if(dto == null) return null;
        return BloqueosFechaEntity.builder()
                .fecha(dto.getFecha())
                .descripcion(dto.getDescripcion())
                .bloqueado(dto.getBloqueado() != null ? dto.getBloqueado() : true)
                .build();
    }
    
    public void updateEntityFromRequest(BloqueosFechaRequestDTO dto, BloqueosFechaEntity entity) {
        if(dto.getDescripcion() != null) {
            entity.setDescripcion(dto.getDescripcion());
        }
        if(dto.getBloqueado() != null) {
            entity.setBloqueado(dto.getBloqueado());
        }
        // La fecha se actualiza en el service por la validación de unicidad
    }
}

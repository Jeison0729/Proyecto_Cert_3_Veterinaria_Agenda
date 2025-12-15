package com.vet.mwoof.proyecto_veterinaria.mapper;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.FestivosRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.FestivosResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.FestivosEntity;
import org.springframework.stereotype.Component;

@Component
public class FestivosMapper {
    
    public FestivosResponseDTO toResponseDTO(FestivosEntity entity) {
        if(entity == null) return null;
        return FestivosResponseDTO.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .descripcion(entity.getDescripcion())
                .bloqueado(entity.getBloqueado())
                .build();
    }
    
    public FestivosEntity toEntity(FestivosRequestDTO dto) {
        if(dto == null) return null;
        return FestivosEntity.builder()
                .fecha(dto.getFecha())
                .descripcion(dto.getDescripcion())
                .bloqueado(dto.getBloqueado() != null ? dto.getBloqueado() : true)
                .build();
    }
    
    public void updateEntityFromRequest(FestivosRequestDTO dto, FestivosEntity entity) {
        if(dto.getDescripcion() != null) {
            entity.setDescripcion(dto.getDescripcion());
        }
        if(dto.getBloqueado() != null) {
            entity.setBloqueado(dto.getBloqueado());
        }
        // fecha se actualiza en el service por validación de unicidad
    }
}

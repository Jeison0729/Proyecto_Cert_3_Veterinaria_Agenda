package com.vet.mwoof.proyecto_veterinaria.mapper;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.CategoriaServicioCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.CategoriaServicioResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.CategoriaServicioEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoriaServicioMapper {
    public CategoriaServicioEntity toEntity(CategoriaServicioCreateRequestDTO request) {
        if(request == null) return null;
        
        return CategoriaServicioEntity.builder()
                .nombre(request.getNombre())
                .build();
    }
    
    public CategoriaServicioResponseDTO toResponse(CategoriaServicioEntity entity) {
        if(entity == null) return null;
        
        return CategoriaServicioResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .build();
    }
}

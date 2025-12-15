package com.vet.mwoof.proyecto_veterinaria.mapper;

import com.vet.mwoof.proyecto_veterinaria.dtos.response.UsuarioResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioResponseDTO toResponse(UsuarioEntity entity) {
        if(entity == null) return null;
        
        return UsuarioResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .usuario(entity.getUsuario())
                .activo(entity.getActivo())
                .build();
    }
}

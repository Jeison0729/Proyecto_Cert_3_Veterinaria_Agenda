package com.vet.mwoof.proyecto_veterinaria.mapper;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.ClienteCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ClienteResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    public ClienteEntity toEntity(ClienteCreateRequestDTO request) {
        if(request == null) return null;
        
        return ClienteEntity.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .dni(request.getDni())
                .celular(request.getCelular())
                .celular2(request.getCelular2())
                .email(request.getEmail())
                .direccion(request.getDireccion())
                .distrito(request.getDistrito())
                .referencia(request.getReferencia())
                .build();
    }
    
    public ClienteResponseDTO toResponse(ClienteEntity entity) {
        if(entity == null) return null;
        
        return ClienteResponseDTO.builder()
                .id(entity.getId())
                .nombres(entity.getNombres())
                .apellidos(entity.getApellidos())
                .dni(entity.getDni())
                .celular(entity.getCelular())
                .celular2(entity.getCelular2())
                .email(entity.getEmail())
                .direccion(entity.getDireccion())
                .distrito(entity.getDistrito())
                .referencia(entity.getReferencia())
                .build();
    }
}

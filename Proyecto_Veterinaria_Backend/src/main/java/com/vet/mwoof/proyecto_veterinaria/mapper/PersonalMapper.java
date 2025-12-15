package com.vet.mwoof.proyecto_veterinaria.mapper;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.PersonalCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.PersonalResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.PersonalEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonalMapper {
    
    public PersonalEntity toEntity(PersonalCreateRequestDTO request) {
        if(request == null) return null;
        
        return PersonalEntity.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .telefono(request.getTelefono())
                .especialidad(request.getEspecialidad() != null ? request.getEspecialidad() : "Estética General")
                .colorCalendario(request.getColorCalendario() != null ? request.getColorCalendario() : "#3498db")
                .build();
    }
    
    public PersonalResponseDTO toResponse(PersonalEntity entity) {
        if(entity == null) return null;
        
        return PersonalResponseDTO.builder()
                .id(entity.getId())
                .nombres(entity.getNombres())
                .apellidos(entity.getApellidos())
                .nombreCompleto(entity.getNombres() + " " + entity.getApellidos())
                .telefono(entity.getTelefono())
                .especialidad(entity.getEspecialidad())
                .colorCalendario(entity.getColorCalendario())
                .build();
    }
}

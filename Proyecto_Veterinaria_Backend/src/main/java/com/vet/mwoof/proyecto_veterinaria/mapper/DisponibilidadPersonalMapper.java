package com.vet.mwoof.proyecto_veterinaria.mapper;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.DisponibilidadPersonalRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.DisponibilidadPersonalResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.DisponibilidadPersonalEntity;
import com.vet.mwoof.proyecto_veterinaria.entity.PersonalEntity;
import org.springframework.stereotype.Component;

@Component
public class DisponibilidadPersonalMapper {
    
    public DisponibilidadPersonalResponseDTO toResponseDTO(DisponibilidadPersonalEntity entity) {
        if(entity == null) return null;
        
        return DisponibilidadPersonalResponseDTO.builder()
                .id(entity.getId())
                .idPersonal(entity.getPersonal().getId())
                .nombrePersonal(entity.getPersonal().getNombres() + " " + entity.getPersonal().getApellidos())
                .fecha(entity.getFecha())
                .horaInicio(entity.getHoraInicio())
                .horaFin(entity.getHoraFin())
                .activo(entity.getActivo())
                .build();
    }
    
    public DisponibilidadPersonalEntity toEntity(DisponibilidadPersonalRequestDTO dto, PersonalEntity personal) {
        return DisponibilidadPersonalEntity.builder()
                .personal(personal)
                .fecha(dto.getFecha())
                .horaInicio(dto.getHoraInicio())
                .horaFin(dto.getHoraFin())
                .activo(dto.getActivo() != null ? dto.getActivo() : true)
                .build();
    }
    
    // Dentro de tu clase DisponibilidadPersonalMapper
    public void updateEntityFromRequest(DisponibilidadPersonalRequestDTO dto, DisponibilidadPersonalEntity entity) {
        if(dto.getFecha() != null) {
            entity.setFecha(dto.getFecha());
        }
        if(dto.getHoraInicio() != null) {
            entity.setHoraInicio(dto.getHoraInicio());
        }
        if(dto.getHoraFin() != null) {
            entity.setHoraFin(dto.getHoraFin());
        }
        if(dto.getActivo() != null) {
            entity.setActivo(dto.getActivo());
        }
    }
    
    
}

package com.vet.mwoof.proyecto_veterinaria.mapper;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.MascotaCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.MascotaResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.MascotaEntity;
import org.springframework.stereotype.Component;

@Component
public class MascotaMapper {
    public MascotaEntity toEntity(MascotaCreateRequestDTO request) {
        if(request == null) return null;
        
        return MascotaEntity.builder()
                .nombre(request.getNombre())
                .raza(request.getRaza())
                .fechaNacimiento(request.getFechaNacimiento())
                .color(request.getColor())
                .esterilizado(request.getEsterilizado())
                .pesoActual(request.getPesoActual())
                .observaciones(request.getObservaciones())
                .foto(request.getFoto())
                .build();
    }
    
    public MascotaResponseDTO toResponse(MascotaEntity entity, String clienteNombreCompleto) {
        if(entity == null) return null;
        
        return MascotaResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .especie(entity.getEspecie().getNombre())
                .raza(entity.getRaza())
                .sexo(entity.getSexo().getNombre())
                .fechaNacimiento(entity.getFechaNacimiento())
                .color(entity.getColor())
                .esterilizado(entity.getEsterilizado())
                .pesoActual(entity.getPesoActual())
                .observaciones(entity.getObservaciones())
                .foto(entity.getFoto())
                .idCliente(entity.getCliente().getId())
                .clienteNombreCompleto(clienteNombreCompleto)
                .build();
    }
}

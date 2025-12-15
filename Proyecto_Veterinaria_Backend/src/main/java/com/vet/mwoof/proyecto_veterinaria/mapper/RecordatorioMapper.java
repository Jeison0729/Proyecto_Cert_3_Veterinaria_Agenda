package com.vet.mwoof.proyecto_veterinaria.mapper;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.RecordatorioRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.RecordatorioResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.RecordatorioEntity;
import org.springframework.stereotype.Component;

@Component
public class RecordatorioMapper {
    
    public RecordatorioEntity toEntity(RecordatorioRequestDTO dto) {
        return RecordatorioEntity.builder()
                .tipo(dto.getTipo())
                .descripcion(dto.getDescripcion())
                .fechaProgramada(dto.getFechaProgramada())
                .medio(dto.getMedio() != null ? dto.getMedio() : "LISTA")
                .enviado(false)
                .build();
    }
    
    public RecordatorioResponseDTO toResponseDTO(RecordatorioEntity entity) {
        if(entity == null) return null;
        return RecordatorioResponseDTO.builder()
                .id(entity.getId())
                .idAgenda(entity.getAgenda().getId())
                .tipo(entity.getTipo())
                .descripcion(entity.getDescripcion())
                .fechaProgramada(entity.getFechaProgramada())
                .enviado(entity.getEnviado())
                .fechaEnvio(entity.getFechaEnvio())
                .medio(entity.getMedio())
                .build();
    }
}

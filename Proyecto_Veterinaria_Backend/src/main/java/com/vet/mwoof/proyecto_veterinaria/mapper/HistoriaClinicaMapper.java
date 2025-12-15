package com.vet.mwoof.proyecto_veterinaria.mapper;

import com.vet.mwoof.proyecto_veterinaria.dtos.response.ArchivoResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.HistoriaClinicaResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.HistoriaClinicaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HistoriaClinicaMapper {
    public HistoriaClinicaResponseDTO toResponse(HistoriaClinicaEntity entity) {
        if(entity == null) return null;
        
        String mascotaNombre = entity.getMascota() != null ? entity.getMascota().getNombre() : "";
        String personalNombre = entity.getPersonal() != null ? entity.getPersonal().getNombres() + " " + entity.getPersonal().getApellidos() : "Sin asignar";
        String estadoNombre = entity.getEstado() != null ? entity.getEstado().getNombre() : "";
        
        List<ArchivoResponseDTO> archivos = entity.getArchivos().stream()
                .map(a -> ArchivoResponseDTO.builder()
                        .idTipoArchivo(a.getTipoArchivo() != null ? a.getTipoArchivo().getId() : null)
                        .tipoNombre(a.getTipoArchivo() != null ? a.getTipoArchivo().getNombre() : "")
                        .nombreArchivo(a.getNombreArchivo())
                        .rutaArchivo(a.getRutaArchivo())
                        .descripcion(a.getDescripcion())
                        .build())
                .toList();
        
        return HistoriaClinicaResponseDTO.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .mascotaNombre(mascotaNombre)
                .personalNombre(personalNombre)
                .motivoConsulta(entity.getMotivoConsulta())
                .diagnostico(entity.getDiagnostico())
                .tratamiento(entity.getTratamiento())
                .fecha(entity.getFecha())
                .horaInicio(entity.getHoraInicio())
                .horaFin(entity.getHoraFin())
                .estadoNombre(estadoNombre)
                .observaciones(entity.getObservaciones())
                .archivos(archivos)
                .build();
    }
}

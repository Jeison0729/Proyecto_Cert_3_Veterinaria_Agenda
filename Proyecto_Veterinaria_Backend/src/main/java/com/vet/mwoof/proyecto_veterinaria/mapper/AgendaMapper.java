package com.vet.mwoof.proyecto_veterinaria.mapper;

import com.vet.mwoof.proyecto_veterinaria.dtos.response.AgendaResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.IngresoServicioDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.RecordatorioResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.AgendaEntity;
import com.vet.mwoof.proyecto_veterinaria.entity.IngresoServicioEntity;
import com.vet.mwoof.proyecto_veterinaria.entity.RecordatorioEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AgendaMapper {
    
    public AgendaResponseDTO toResponseDTO(AgendaEntity entity) {
        if(entity == null) return null;
        
        AgendaResponseDTO dto = new AgendaResponseDTO();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setIdCliente(entity.getCliente().getId());
        dto.setClienteNombre(entity.getCliente().getNombres() + " " + entity.getCliente().getApellidos());
        dto.setIdMascota(entity.getMascota().getId());
        dto.setMascotaNombre(entity.getMascota().getNombre());
        
        if(entity.getPersonal() != null) {
            dto.setIdPersonal(entity.getPersonal().getId());
            dto.setPersonalNombre(entity.getPersonal().getNombres() + " " + entity.getPersonal().getApellidos());
        }
        
        dto.setFecha(entity.getFecha());
        dto.setHora(entity.getHora());
        dto.setDuracionEstimadaMin(entity.getDuracionEstimadaMin());
        dto.setAbonoInicial(entity.getAbonoInicial());
        dto.setTotalCita(entity.getTotalCita());
        dto.setIdEstado(entity.getEstado().getId());
        dto.setEstadoNombre(entity.getEstado().getNombre());
        
        if(entity.getMedioSolicitud() != null) {
            dto.setIdMedioSolicitud(entity.getMedioSolicitud().getId());
            dto.setMedioSolicitudNombre(entity.getMedioSolicitud().getNombre());
        }
        
        dto.setObservaciones(entity.getObservaciones());
        dto.setFechaRegistro(entity.getFechaRegistro());
        
        if(entity.getServicios() != null && ! entity.getServicios().isEmpty()) {
            dto.setServicios(entity.getServicios().stream()
                    .map(this :: toIngresoDTO)
                    .collect(Collectors.toList()));
        }
        
        if(entity.getRecordatorios() != null && ! entity.getRecordatorios().isEmpty()) {
            dto.setRecordatorios(entity.getRecordatorios().stream()
                    .map(this :: toRecordatorioDTO)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    private IngresoServicioDTO toIngresoDTO(IngresoServicioEntity entity) {
        return IngresoServicioDTO.builder()
                .id(entity.getId())
                .idAgenda(entity.getAgenda().getId())
                .idServicio(entity.getServicio().getId())
                .nombreServicio(entity.getServicio().getNombre())
                .idPersonal(entity.getPersonal().getId())
                .nombrePersonal(entity.getPersonal().getNombres() + " " + entity.getPersonal().getApellidos())
                .cantidad(entity.getCantidad())
                .duracionMin(entity.getDuracionMin())
                .valorServicio(entity.getValorServicio())
                .adicionales(entity.getAdicionales())
                .observaciones(entity.getObservaciones())
                .build();
    }
    
    private RecordatorioResponseDTO toRecordatorioDTO(RecordatorioEntity entity) {
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

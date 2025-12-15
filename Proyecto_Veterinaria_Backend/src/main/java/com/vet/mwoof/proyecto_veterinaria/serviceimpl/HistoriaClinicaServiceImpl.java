package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.ArchivoRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.request.HistoriaClinicaCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.HistoriaClinicaResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.*;
import com.vet.mwoof.proyecto_veterinaria.mapper.HistoriaClinicaMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.*;
import com.vet.mwoof.proyecto_veterinaria.service.HistoriaClinicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoriaClinicaServiceImpl implements HistoriaClinicaService {
    private final HistoriaClinicaRepository historiaRepo;
    private final MascotaRepository mascotaRepo;
    private final PersonalRepository personalRepo;
    private final EstadoHistoriaClinicaRepository estadoRepo;
    private final TipoArchivoClinicoRepository tipoArchivoRepo;
    private final HistoriaClinicaArchivoRepository archivoRepo;
    private final HistoriaClinicaMapper mapper;
    
    @Override
    @Transactional
    public HistoriaClinicaResponseDTO crear(HistoriaClinicaCreateRequestDTO request) {
        MascotaEntity mascota = mascotaRepo.findById(request.getIdMascota())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        
        PersonalEntity personal = request.getIdPersonal() != null ?
                personalRepo.findById(request.getIdPersonal()).orElse(null) : null;
        
        EstadoHistoriaEntity estado = estadoRepo.findById(request.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        
        HistoriaClinicaEntity historia = HistoriaClinicaEntity.builder()
                .codigo("HC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .mascota(mascota)
                .personal(personal)
                .motivoConsulta(request.getMotivoConsulta())
                .diagnostico(request.getDiagnostico())
                .tratamiento(request.getTratamiento())
                .fecha(request.getFecha())
                .horaInicio(request.getHoraInicio())
                .horaFin(request.getHoraFin())
                .estado(estado)
                .observaciones(request.getObservaciones())
                .build();
        
        if (request.getArchivos() != null) {
            for (ArchivoRequestDTO a : request.getArchivos()) {
                TipoArchivoEntity tipo = a.getIdTipoArchivo() != null ?
                        tipoArchivoRepo.findById(a.getIdTipoArchivo()).orElse(null) : null;
                
                HistoriaClinicaArchivoEntity archivo = HistoriaClinicaArchivoEntity.builder()
                        .historiaClinica(historia)
                        .tipoArchivo(tipo)
                        .nombreArchivo(a.getNombreArchivo())
                        .rutaArchivo(a.getRutaArchivo())
                        .descripcion(a.getDescripcion())
                        .build();
                
                historia.getArchivos().add(archivo);
            }
        }
        
        HistoriaClinicaEntity saved = historiaRepo.save(historia);
        return mapper.toResponse(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<HistoriaClinicaResponseDTO> listarPorMascota(Long idMascota) {
        return historiaRepo.findById(idMascota).stream()
                .map(mapper::toResponse)
                .toList();
    }
}

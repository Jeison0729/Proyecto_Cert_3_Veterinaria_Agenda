package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.RecordatorioRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.RecordatorioResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.AgendaEntity;
import com.vet.mwoof.proyecto_veterinaria.entity.RecordatorioEntity;
import com.vet.mwoof.proyecto_veterinaria.mapper.RecordatorioMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.AgendaRepository;
import com.vet.mwoof.proyecto_veterinaria.repository.RecordatorioRepository;
import com.vet.mwoof.proyecto_veterinaria.service.RecordatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordatorioServiceImpl implements RecordatorioService {
    
    private final RecordatorioRepository recordatorioRepository;
    private final AgendaRepository agendaRepository;
    private final RecordatorioMapper mapper;
    
    @Override
    @Transactional
    public RecordatorioResponseDTO crearRecordatorio(RecordatorioRequestDTO dto) {
        AgendaEntity agenda = agendaRepository.findById(dto.getIdAgenda())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        RecordatorioEntity entity = mapper.toEntity(dto);
        entity.setAgenda(agenda);
        RecordatorioEntity guardado = recordatorioRepository.save(entity);
        return mapper.toResponseDTO(guardado);
    }
    
    @Override
    @Transactional
    public void generarRecordatorioPostCita(Long idAgenda, String medio) {
        AgendaEntity agenda = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        if(! "COMPLETADA".equals(agenda.getEstado().getNombre())) return;
        
        RecordatorioEntity entity = RecordatorioEntity.builder()
                .agenda(agenda)
                .tipo("POST_CITA")
                .descripcion("Seguimiento para " + agenda.getMascota().getNombre())
                .fechaProgramada(LocalDateTime.now().plusDays(7))
                .medio(medio != null ? medio : "LISTA")
                .enviado(false)
                .build();
        
        recordatorioRepository.save(entity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<RecordatorioResponseDTO> obtenerRecordatoriosPendientes() {
        return recordatorioRepository.findByEnviadoFalse().stream()
                .map(mapper :: toResponseDTO)
                .toList();
    }
    
    @Override
    @Transactional
    public RecordatorioResponseDTO marcarEnviado(Long id) {
        RecordatorioEntity entity = recordatorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recordatorio no encontrado"));
        entity.setEnviado(true);
        entity.setFechaEnvio(LocalDateTime.now());
        return mapper.toResponseDTO(recordatorioRepository.save(entity));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<RecordatorioResponseDTO> obtenerRecordatoriosCita(Long idAgenda) {
        return recordatorioRepository.findByAgendaId(idAgenda).stream()
                .map(mapper :: toResponseDTO)
                .toList();
    }
}

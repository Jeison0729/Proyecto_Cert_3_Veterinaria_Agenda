package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.BloqueosFechaRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.BloqueosFechaResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.BloqueosFechaEntity;
import com.vet.mwoof.proyecto_veterinaria.mapper.BloqueosFechaMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.BloqueosFechaRepository;
import com.vet.mwoof.proyecto_veterinaria.service.BloqueosFechaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BloqueosFechaServiceImpl implements BloqueosFechaService {
    
    private final BloqueosFechaRepository bloqueoRepository;
    private final BloqueosFechaMapper bloqueoMapper;
    
    @Override
    @Transactional
    public BloqueosFechaResponseDTO crearBloqueo(BloqueosFechaRequestDTO dto) {
        if(dto.getFecha() == null) {
            throw new IllegalArgumentException("La fecha del bloqueo es obligatoria");
        }
        
        bloqueoRepository.findByFecha(dto.getFecha())
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe un bloqueo para la fecha: " + dto.getFecha());
                });
        
        BloqueosFechaEntity entity = bloqueoMapper.toEntity(dto);
        BloqueosFechaEntity saved = bloqueoRepository.save(entity);
        return bloqueoMapper.toResponseDTO(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BloqueosFechaResponseDTO> listarBloqueos() {
        return bloqueoRepository.findAll().stream()
                .map(bloqueoMapper :: toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public BloqueosFechaResponseDTO obtenerBloqueo(Integer id) {
        BloqueosFechaEntity entity = bloqueoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloqueo no encontrado con id: " + id));
        return bloqueoMapper.toResponseDTO(entity);
    }
    
    @Override
    @Transactional
    public BloqueosFechaResponseDTO actualizarBloqueo(Integer id, BloqueosFechaRequestDTO dto) {
        BloqueosFechaEntity entity = bloqueoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloqueo no encontrado con id: " + id));
        
        if(dto.getFecha() != null && ! dto.getFecha().equals(entity.getFecha())) {
            bloqueoRepository.findByFecha(dto.getFecha())
                    .ifPresent(e -> {
                        if(! e.getId().equals(id)) {
                            throw new IllegalArgumentException("Ya existe un bloqueo para la fecha: " + dto.getFecha());
                        }
                    });
            entity.setFecha(dto.getFecha());
        }
        
        bloqueoMapper.updateEntityFromRequest(dto, entity);
        BloqueosFechaEntity updated = bloqueoRepository.save(entity);
        return bloqueoMapper.toResponseDTO(updated);
    }
    
    @Override
    @Transactional
    public void eliminarBloqueo(Integer id) {
        if(! bloqueoRepository.existsById(id)) {
            throw new RuntimeException("Bloqueo no encontrado con id: " + id);
        }
        bloqueoRepository.deleteById(id);
    }
}

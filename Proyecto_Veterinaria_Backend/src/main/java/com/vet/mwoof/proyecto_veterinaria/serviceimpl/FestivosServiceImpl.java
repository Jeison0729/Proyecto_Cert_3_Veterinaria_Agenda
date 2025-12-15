package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.FestivosRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.FestivosResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.FestivosEntity;
import com.vet.mwoof.proyecto_veterinaria.mapper.FestivosMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.FestivosRepository;
import com.vet.mwoof.proyecto_veterinaria.service.FestivosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FestivosServiceImpl implements FestivosService {
    
    private final FestivosRepository festivosRepository;
    private final FestivosMapper festivosMapper;
    
    @Override
    @Transactional
    public FestivosResponseDTO crearFestivo(FestivosRequestDTO dto) {
        if (dto.getFecha() == null) {
            throw new IllegalArgumentException("La fecha del festivo es obligatoria");
        }
        
        festivosRepository.findByFecha(dto.getFecha())
                .ifPresent(e -> {
                    throw new IllegalArgumentException("Ya existe un festivo en la fecha: " + dto.getFecha());
                });
        
        FestivosEntity entity = festivosMapper.toEntity(dto);
        FestivosEntity saved = festivosRepository.save(entity);
        return festivosMapper.toResponseDTO(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<FestivosResponseDTO> listarFestivos() {
        return festivosRepository.findAll().stream()
                .map(festivosMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public FestivosResponseDTO obtenerFestivo(LocalDate fecha) {
        return festivosRepository.findByFecha(fecha)
                .map(festivosMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Festivo no encontrado para la fecha: " + fecha));
    }
    
    @Override
    @Transactional
    public FestivosResponseDTO actualizarFestivo(Integer id, FestivosRequestDTO dto) {
        FestivosEntity entity = festivosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Festivo no encontrado con id: " + id));
        
        if (dto.getFecha() != null && !dto.getFecha().equals(entity.getFecha())) {
            festivosRepository.findByFecha(dto.getFecha())
                    .ifPresent(e -> {
                        if (!e.getId().equals(id)) {
                            throw new IllegalArgumentException("Ya existe un festivo en la fecha: " + dto.getFecha());
                        }
                    });
            entity.setFecha(dto.getFecha());
        }
        
        festivosMapper.updateEntityFromRequest(dto, entity);
        FestivosEntity updated = festivosRepository.save(entity);
        return festivosMapper.toResponseDTO(updated);
    }
    
    @Override
    @Transactional
    public void eliminarFestivo(Integer id) {
        if (!festivosRepository.existsById(id)) {
            throw new RuntimeException("Festivo no encontrado con id: " + id);
        }
        festivosRepository.deleteById(id);
    }
}

package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.ServicioCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ServicioResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.ServicioEntity;
import com.vet.mwoof.proyecto_veterinaria.mapper.ServicioMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.ServicioRepository;
import com.vet.mwoof.proyecto_veterinaria.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicioServiceImpl implements ServicioService {
    
    private final ServicioRepository repository;
    private final ServicioMapper mapper;
    
    @Override
    @Transactional
    public ServicioResponseDTO crear(ServicioCreateRequestDTO request) {
        ServicioEntity entity = mapper.toEntity(request);
        ServicioEntity saved = repository.save(entity);
        return mapper.toResponse(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ServicioResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper :: toResponse)
                .collect(Collectors.toList());
    }
}

package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.CategoriaServicioCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.CategoriaServicioResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.CategoriaServicioEntity;
import com.vet.mwoof.proyecto_veterinaria.mapper.CategoriaServicioMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.CategoriaServicioRepository;
import com.vet.mwoof.proyecto_veterinaria.service.CategoriaServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServicioServiceImpl implements CategoriaServicioService {
    
    private final CategoriaServicioRepository categoriaServicioRepository;
    private final CategoriaServicioMapper categoriaServicioMapper;
    
    @Override
    @Transactional
    public CategoriaServicioResponseDTO crear(CategoriaServicioCreateRequestDTO request) {
        CategoriaServicioEntity entity = categoriaServicioMapper.toEntity(request);
        CategoriaServicioEntity saved = categoriaServicioRepository.save(entity);
        return categoriaServicioMapper.toResponse(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoriaServicioResponseDTO> listarTodas() {
        return categoriaServicioRepository.findAll()
                .stream()
                .map(categoriaServicioMapper::toResponse)
                .collect(Collectors.toList());
    }
}

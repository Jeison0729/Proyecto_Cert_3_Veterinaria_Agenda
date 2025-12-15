package com.vet.mwoof.proyecto_veterinaria.mapper;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.ServicioCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ServicioResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.CategoriaServicioEntity;
import com.vet.mwoof.proyecto_veterinaria.entity.ServicioEntity;
import com.vet.mwoof.proyecto_veterinaria.repository.CategoriaServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServicioMapper {
    private final CategoriaServicioRepository catRepo;
    
    public ServicioEntity toEntity(ServicioCreateRequestDTO request) {
        if(request == null) return null;
        
        CategoriaServicioEntity cat = catRepo.findById(request.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        return ServicioEntity.builder()
                .nombre(request.getNombre())
                .categoria(cat)
                .duracionMinutos(request.getDuracionMinutos())
                .precio(request.getPrecio())
                .descripcion(request.getDescripcion())
                .build();
    }
    
    public ServicioResponseDTO toResponse(ServicioEntity entity) {
        if(entity == null) return null;
        
        return ServicioResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .idCategoria(entity.getCategoria().getId())
                .categoriaNombre(entity.getCategoria().getNombre())
                .duracionMinutos(entity.getDuracionMinutos())
                .precio(entity.getPrecio())
                .descripcion(entity.getDescripcion())
                .build();
    }
}

package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.CategoriaServicioCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.CategoriaServicioResponseDTO;

import java.util.List;

public interface CategoriaServicioService {
    CategoriaServicioResponseDTO crear(CategoriaServicioCreateRequestDTO request);
    
    List<CategoriaServicioResponseDTO> listarTodas();
}

package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.ServicioCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ServicioResponseDTO;

import java.util.List;

public interface ServicioService {
    ServicioResponseDTO crear(ServicioCreateRequestDTO request);
    
    List<ServicioResponseDTO> listarTodos();
}

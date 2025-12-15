package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.BloqueosFechaRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.BloqueosFechaResponseDTO;

import java.util.List;

public interface BloqueosFechaService {
    BloqueosFechaResponseDTO crearBloqueo(BloqueosFechaRequestDTO dto);
    
    List<BloqueosFechaResponseDTO> listarBloqueos();
    
    BloqueosFechaResponseDTO obtenerBloqueo(Integer id);
    
    void eliminarBloqueo(Integer id);
    
    BloqueosFechaResponseDTO actualizarBloqueo(Integer id, BloqueosFechaRequestDTO dto);
    
    
}

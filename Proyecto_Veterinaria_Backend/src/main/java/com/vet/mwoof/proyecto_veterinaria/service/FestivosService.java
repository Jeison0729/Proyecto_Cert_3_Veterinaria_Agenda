package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.FestivosRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.FestivosResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface FestivosService {
    
    FestivosResponseDTO crearFestivo(FestivosRequestDTO dto);
    
    List<FestivosResponseDTO> listarFestivos();
    
    FestivosResponseDTO obtenerFestivo(LocalDate fecha);
    
    FestivosResponseDTO actualizarFestivo(Integer id, FestivosRequestDTO dto);
    
    void eliminarFestivo(Integer id);
}

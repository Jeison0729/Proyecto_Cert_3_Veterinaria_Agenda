package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.DisponibilidadPersonalRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.DisponibilidadPersonalResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.DisponibilidadPersonalResumenDTO;

import java.time.LocalDate;
import java.util.List;

public interface DisponibilidadPersonalService {
    DisponibilidadPersonalResponseDTO crearDisponibilidad(DisponibilidadPersonalRequestDTO dto);
    
    List<DisponibilidadPersonalResponseDTO> obtenerDisponibilidadPeriodo(
            Long idPersonal, LocalDate fechaInicio, LocalDate fechaFin);
    
    DisponibilidadPersonalResumenDTO obtenerResumenPersonal(Long idPersonal);
    
    DisponibilidadPersonalResponseDTO actualizarDisponibilidad(
            Long id, DisponibilidadPersonalRequestDTO dto);
    
    void eliminarDisponibilidad(Long id);
    
}

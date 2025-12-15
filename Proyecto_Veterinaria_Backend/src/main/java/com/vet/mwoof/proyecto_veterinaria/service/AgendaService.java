package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.AgendaCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.AgendaResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AgendaService {
    
    
    AgendaResponseDTO crearCita(AgendaCreateRequestDTO request);
    
    AgendaResponseDTO actualizarCita(Long id, AgendaCreateRequestDTO request);
    
    AgendaResponseDTO obtenerCita(Long id);
    
    List<AgendaResponseDTO> listarCitasPeriodo(LocalDate fechaInicio, LocalDate fechaFin);
    
    List<AgendaResponseDTO> listarCitasCliente(Long idCliente);
    
    List<AgendaResponseDTO> listarCitasMascota(Long idMascota);
    
    AgendaResponseDTO reasignarCita(Long idCita, LocalDate nuevaFecha, LocalTime nuevaHora);
    
    AgendaResponseDTO cancelarCita(Long id, String motivo, Integer usuarioId);
    
    AgendaResponseDTO completarCita(Long id, Integer usuarioId);
    
    
}

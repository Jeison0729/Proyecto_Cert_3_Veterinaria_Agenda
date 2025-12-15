package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.response.HorarioAtencionDTO;

import java.util.List;

public interface HorarioAtencionService {
    
    HorarioAtencionDTO crearHorario(HorarioAtencionDTO dto);
    
    List<HorarioAtencionDTO> listarHorariosActivos();
    
    HorarioAtencionDTO obtenerHorarioPorDia(Integer diaSemana);
    
    HorarioAtencionDTO actualizarHorario(Integer diaSemana, HorarioAtencionDTO dto);
    
}

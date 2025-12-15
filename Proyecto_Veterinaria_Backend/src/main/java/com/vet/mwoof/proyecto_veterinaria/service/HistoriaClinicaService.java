package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.HistoriaClinicaCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.HistoriaClinicaResponseDTO;

import java.util.List;

public interface HistoriaClinicaService {
    HistoriaClinicaResponseDTO crear(HistoriaClinicaCreateRequestDTO request);
    
    List<HistoriaClinicaResponseDTO> listarPorMascota(Long idMascota);
}

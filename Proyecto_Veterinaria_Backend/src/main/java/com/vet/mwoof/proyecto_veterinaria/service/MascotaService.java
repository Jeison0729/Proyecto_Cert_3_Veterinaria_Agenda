package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.MascotaCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.MascotaResponseDTO;

import java.util.List;

public interface MascotaService {
    MascotaResponseDTO crear(MascotaCreateRequestDTO request);
    
    MascotaResponseDTO obtenerPorId(Long id);
    
    List<MascotaResponseDTO> listarTodas();
    
    List<MascotaResponseDTO> listarPorCliente(Long idCliente);
    
    MascotaResponseDTO actualizar(Long id, MascotaCreateRequestDTO request);
    
    void eliminar(Long id);
}

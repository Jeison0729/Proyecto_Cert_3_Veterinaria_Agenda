package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.ClienteCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ClienteResponseDTO;

import java.util.List;


public interface ClienteService {
    ClienteResponseDTO crear(ClienteCreateRequestDTO request);
    
    ClienteResponseDTO obtenerPorId(Long id);
    
    List<ClienteResponseDTO> listarTodos();
    
    ClienteResponseDTO actualizar(Long id, ClienteCreateRequestDTO request);
    
    void eliminar(Long id);
}

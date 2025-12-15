package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.PersonalCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.PersonalResponseDTO;

import java.util.List;

public interface PersonalService {
    PersonalResponseDTO crear(PersonalCreateRequestDTO request);
    
    PersonalResponseDTO obtenerPorId(Long id);
    
    List<PersonalResponseDTO> listarTodos();
    
    PersonalResponseDTO actualizar(Long id, PersonalCreateRequestDTO request);
    
    void eliminar(Long id);
}

package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.UsuarioLoginRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.UsuarioResponseDTO;

public interface UsuarioService {
    UsuarioResponseDTO login(UsuarioLoginRequestDTO request);
}

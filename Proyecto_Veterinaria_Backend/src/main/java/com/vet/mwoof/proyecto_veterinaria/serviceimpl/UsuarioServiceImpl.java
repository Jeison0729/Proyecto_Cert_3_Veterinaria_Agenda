package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.UsuarioLoginRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.UsuarioResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.UsuarioEntity;
import com.vet.mwoof.proyecto_veterinaria.mapper.UsuarioMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.UsuarioRepository;
import com.vet.mwoof.proyecto_veterinaria.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    
    @Override
    public UsuarioResponseDTO login(UsuarioLoginRequestDTO request) {
        UsuarioEntity usuario = usuarioRepository.findByUsuarioAndPassword(request.getUsuario(), request.getPassword())
                .orElseThrow(() -> new RuntimeException("Usuario o contraseña incorrectos"));
        
        return usuarioMapper.toResponse(usuario);
    }
}

package com.vet.mwoof.proyecto_veterinaria.controller;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.UsuarioLoginRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ApiResponse;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.UsuarioResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioRestController {
    private final UsuarioService usuarioService;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> login(@RequestBody UsuarioLoginRequestDTO request) {
        try {
            UsuarioResponseDTO usuario = usuarioService.login(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Login exitoso", usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}

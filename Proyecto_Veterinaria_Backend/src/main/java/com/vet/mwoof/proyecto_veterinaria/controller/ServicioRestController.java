package com.vet.mwoof.proyecto_veterinaria.controller;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.ServicioCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ApiResponse;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ServicioResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioRestController {
    
    private final ServicioService service;
    
    @PostMapping
    public ResponseEntity<ApiResponse<ServicioResponseDTO>> crear(@RequestBody ServicioCreateRequestDTO request) {
        try {
            ServicioResponseDTO creado = service.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Servicio creado", creado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<ServicioResponseDTO>>> listar() {
        List<ServicioResponseDTO> lista = service.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de servicios", lista));
    }
}

package com.vet.mwoof.proyecto_veterinaria.controller;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.CategoriaServicioCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ApiResponse;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.CategoriaServicioResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.service.CategoriaServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/categorias-servicio")
@RequiredArgsConstructor
public class CategoriaServicioController {
    
    private final CategoriaServicioService service;
    
    @PostMapping
    public ResponseEntity<ApiResponse<CategoriaServicioResponseDTO>> crear(@RequestBody CategoriaServicioCreateRequestDTO request) {
        try {
            CategoriaServicioResponseDTO creado = service.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Categoría creada", creado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoriaServicioResponseDTO>>> listar() {
        List<CategoriaServicioResponseDTO> lista = service.listarTodas();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de categorías", lista));
    }
}

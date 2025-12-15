package com.vet.mwoof.proyecto_veterinaria.controller;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.BloqueosFechaRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ApiResponse;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.BloqueosFechaResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.service.BloqueosFechaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/bloqueos")
@RequiredArgsConstructor
public class BloqueosFechaRestController {
    
    private final BloqueosFechaService bloqueoService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<BloqueosFechaResponseDTO>> crear(@RequestBody BloqueosFechaRequestDTO request) {
        try {
            BloqueosFechaResponseDTO creado = bloqueoService.crearBloqueo(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Bloqueo creado correctamente", creado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<BloqueosFechaResponseDTO>>> listar() {
        try {
            List<BloqueosFechaResponseDTO> bloqueos = bloqueoService.listarBloqueos();
            return ResponseEntity.ok(new ApiResponse<>(true, "Lista de bloqueos", bloqueos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BloqueosFechaResponseDTO>> obtener(@PathVariable Integer id) {
        try {
            BloqueosFechaResponseDTO bloqueo = bloqueoService.obtenerBloqueo(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Bloqueo encontrado", bloqueo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BloqueosFechaResponseDTO>> actualizar(
            @PathVariable Integer id,
            @RequestBody BloqueosFechaRequestDTO request
    ) {
        try {
            BloqueosFechaResponseDTO actualizado = bloqueoService.actualizarBloqueo(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Bloqueo actualizado correctamente", actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        try {
            bloqueoService.eliminarBloqueo(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Bloqueo eliminado correctamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}

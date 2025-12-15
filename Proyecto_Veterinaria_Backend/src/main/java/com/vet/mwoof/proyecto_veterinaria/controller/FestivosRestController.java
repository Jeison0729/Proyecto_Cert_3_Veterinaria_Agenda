package com.vet.mwoof.proyecto_veterinaria.controller;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.FestivosRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ApiResponse;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.FestivosResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.service.FestivosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/festivos")
@RequiredArgsConstructor
public class FestivosRestController {
    
    private final FestivosService festivosService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<FestivosResponseDTO>> crear(@Valid @RequestBody FestivosRequestDTO request) {
        try {
            FestivosResponseDTO creado = festivosService.crearFestivo(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Festivo creado correctamente", creado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<FestivosResponseDTO>>> listar() {
        try {
            List<FestivosResponseDTO> festivos = festivosService.listarFestivos();
            return ResponseEntity.ok(new ApiResponse<>(true, "Lista de festivos", festivos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<ApiResponse<FestivosResponseDTO>> obtenerPorFecha(@PathVariable LocalDate fecha) {
        try {
            FestivosResponseDTO festivo = festivosService.obtenerFestivo(fecha);
            return ResponseEntity.ok(new ApiResponse<>(true, "Festivo encontrado", festivo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FestivosResponseDTO>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody FestivosRequestDTO request
    ) {
        try {
            FestivosResponseDTO actualizado = festivosService.actualizarFestivo(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Festivo actualizado correctamente", actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        try {
            festivosService.eliminarFestivo(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Festivo eliminado correctamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}

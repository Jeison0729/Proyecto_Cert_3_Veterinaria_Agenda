package com.vet.mwoof.proyecto_veterinaria.controller;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.DisponibilidadPersonalRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ApiResponse;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.DisponibilidadPersonalResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.service.DisponibilidadPersonalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/disponibilidad-personal")
@RequiredArgsConstructor
public class DisponibilidadPersonalRestController {
    
    private final DisponibilidadPersonalService service;
    
    @PostMapping
    public ResponseEntity<ApiResponse<DisponibilidadPersonalResponseDTO>> crear(
            @Valid @RequestBody DisponibilidadPersonalRequestDTO request) {
        try {
            var creado = service.crearDisponibilidad(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Disponibilidad creada", creado));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/personal/{idPersonal}")
    public ResponseEntity<ApiResponse<List<DisponibilidadPersonalResponseDTO>>> porPeriodo(
            @PathVariable Long idPersonal,
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fin) {
        var lista = service.obtenerDisponibilidadPeriodo(idPersonal, inicio, fin);
        return ResponseEntity.ok(new ApiResponse<>(true, "Disponibilidades encontradas", lista));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DisponibilidadPersonalResponseDTO>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DisponibilidadPersonalRequestDTO request) {
        try {
            var actualizado = service.actualizarDisponibilidad(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Actualizado correctamente", actualizado));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminarDisponibilidad(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Eliminado correctamente", null));
    }
}

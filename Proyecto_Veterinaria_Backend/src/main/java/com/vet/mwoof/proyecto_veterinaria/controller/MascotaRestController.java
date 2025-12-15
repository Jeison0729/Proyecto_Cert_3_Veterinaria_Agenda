package com.vet.mwoof.proyecto_veterinaria.controller;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.MascotaCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ApiResponse;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.MascotaResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.service.MascotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaRestController {
    private final MascotaService mascotaService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<MascotaResponseDTO>> crear(@RequestBody MascotaCreateRequestDTO request) {
        try {
            MascotaResponseDTO creado = mascotaService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Mascota creada correctamente", creado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<MascotaResponseDTO>>> listarTodas() {
        List<MascotaResponseDTO> lista = mascotaService.listarTodas();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de mascotas", lista));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MascotaResponseDTO>> obtener(@PathVariable Long id) {
        try {
            MascotaResponseDTO mascota = mascotaService.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Mascota encontrada", mascota));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<ApiResponse<List<MascotaResponseDTO>>> listarPorCliente(@PathVariable Long idCliente) {
        List<MascotaResponseDTO> lista = mascotaService.listarPorCliente(idCliente);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mascotas del cliente", lista));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MascotaResponseDTO>> actualizar(@PathVariable Long id, @RequestBody MascotaCreateRequestDTO request) {
        try {
            MascotaResponseDTO actualizado = mascotaService.actualizar(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Mascota actualizada", actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            mascotaService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Mascota eliminada", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}

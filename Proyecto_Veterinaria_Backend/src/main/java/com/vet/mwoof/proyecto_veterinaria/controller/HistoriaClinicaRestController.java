package com.vet.mwoof.proyecto_veterinaria.controller;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.HistoriaClinicaCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ApiResponse;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.HistoriaClinicaResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.service.HistoriaClinicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/historia-clinica")
@RequiredArgsConstructor
public class HistoriaClinicaRestController {
    private final HistoriaClinicaService historiaService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<HistoriaClinicaResponseDTO>> crear(@RequestBody HistoriaClinicaCreateRequestDTO request) {
        try {
            HistoriaClinicaResponseDTO creado = historiaService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Historia clínica creada", creado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<ApiResponse<List<HistoriaClinicaResponseDTO>>> listarPorMascota(@PathVariable Long idMascota) {
        List<HistoriaClinicaResponseDTO> lista = historiaService.listarPorMascota(idMascota);
        return ResponseEntity.ok(new ApiResponse<>(true, "Historia clínica de la mascota", lista));
    }
}

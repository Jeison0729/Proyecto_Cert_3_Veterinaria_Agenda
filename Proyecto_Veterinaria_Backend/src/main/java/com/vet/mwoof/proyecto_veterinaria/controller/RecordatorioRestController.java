package com.vet.mwoof.proyecto_veterinaria.controller;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.RecordatorioRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ApiResponse;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.RecordatorioResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.service.RecordatorioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/recordatorios")
@RequiredArgsConstructor
public class RecordatorioRestController {
    
    private final RecordatorioService recordatorioService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<RecordatorioResponseDTO>> crear(
            @Valid @RequestBody RecordatorioRequestDTO request
    ) {
        try {
            var creado = recordatorioService.crearRecordatorio(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Recordatorio creado correctamente", creado));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/pendientes")
    public ResponseEntity<ApiResponse<List<RecordatorioResponseDTO>>> pendientes() {
        var lista = recordatorioService.obtenerRecordatoriosPendientes();
        return ResponseEntity.ok(new ApiResponse<>(true, "Recordatorios pendientes", lista));
    }
    
    @GetMapping("/cita/{idAgenda}")
    public ResponseEntity<ApiResponse<List<RecordatorioResponseDTO>>> porCita(@PathVariable Long idAgenda) {
        var lista = recordatorioService.obtenerRecordatoriosCita(idAgenda);
        return ResponseEntity.ok(new ApiResponse<>(true, "Recordatorios de la cita", lista));
    }
    
    @PutMapping("/{id}/enviar")
    public ResponseEntity<ApiResponse<RecordatorioResponseDTO>> marcarEnsviado(@PathVariable Long id) {
        try {
            var enviado = recordatorioService.marcarEnviado(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Recordatorio marcado como enviado", enviado));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}

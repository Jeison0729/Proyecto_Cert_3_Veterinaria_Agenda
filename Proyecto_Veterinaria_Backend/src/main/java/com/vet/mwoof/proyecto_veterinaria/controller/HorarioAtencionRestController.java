package com.vet.mwoof.proyecto_veterinaria.controller;

import com.vet.mwoof.proyecto_veterinaria.dtos.response.ApiResponse;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.HorarioAtencionDTO;
import com.vet.mwoof.proyecto_veterinaria.service.HorarioAtencionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
public class HorarioAtencionRestController {
    
    private final HorarioAtencionService horarioService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<HorarioAtencionDTO>> crear(@RequestBody HorarioAtencionDTO request) {
        try {
            HorarioAtencionDTO creado = horarioService.crearHorario(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Horario creado correctamente", creado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<HorarioAtencionDTO>>> listar() {
        try {
            List<HorarioAtencionDTO> horarios = horarioService.listarHorariosActivos();
            return ResponseEntity.ok(new ApiResponse<>(true, "Horarios activos", horarios));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/dia/{diaSemana}")
    public ResponseEntity<ApiResponse<HorarioAtencionDTO>> obtenerPorDia(@PathVariable Integer diaSemana) {
        try {
            HorarioAtencionDTO horario = horarioService.obtenerHorarioPorDia(diaSemana);
            return ResponseEntity.ok(new ApiResponse<>(true, "Horario encontrado", horario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @PutMapping("/dia/{diaSemana}")
    public ResponseEntity<ApiResponse<HorarioAtencionDTO>> actualizar(
            @PathVariable Integer diaSemana,
            @RequestBody HorarioAtencionDTO request
    ) {
        try {
            HorarioAtencionDTO actualizado = horarioService.actualizarHorario(diaSemana, request);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Horario actualizado correctamente", actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}

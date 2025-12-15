package com.vet.mwoof.proyecto_veterinaria.controller;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.PersonalCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ApiResponse;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.PersonalResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.service.PersonalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/personal")
@RequiredArgsConstructor
public class PersonalRestController {
    private final PersonalService personalService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<PersonalResponseDTO>> crear(@RequestBody PersonalCreateRequestDTO request) {
        try {
            PersonalResponseDTO creado = personalService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Personal creado correctamente", creado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<PersonalResponseDTO>>> listar() {
        List<PersonalResponseDTO> lista = personalService.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de personal", lista));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PersonalResponseDTO>> obtener(@PathVariable Long id) {
        try {
            PersonalResponseDTO personal = personalService.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Personal encontrado", personal));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PersonalResponseDTO>> actualizar(@PathVariable Long id, @RequestBody PersonalCreateRequestDTO request) {
        try {
            PersonalResponseDTO actualizado = personalService.actualizar(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Personal actualizado", actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            personalService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Personal eliminado", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}

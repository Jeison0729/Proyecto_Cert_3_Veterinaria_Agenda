package com.vet.mwoof.proyecto_veterinaria.controller;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.ClienteCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ApiResponse;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ClienteResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteRestController {
    private final ClienteService clienteService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponseDTO>> crear(@RequestBody ClienteCreateRequestDTO request) {
        try {
            ClienteResponseDTO creado = clienteService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Cliente creado correctamente", creado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<ClienteResponseDTO>>> listar() {
        List<ClienteResponseDTO> lista = clienteService.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de clientes", lista));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponseDTO>> obtener(@PathVariable Long id) {
        try {
            ClienteResponseDTO cliente = clienteService.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cliente encontrado", cliente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponseDTO>> actualizar(@PathVariable Long id, @RequestBody ClienteCreateRequestDTO request) {
        try {
            ClienteResponseDTO actualizado = clienteService.actualizar(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cliente actualizado", actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            clienteService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cliente eliminado", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}

package com.vet.mwoof.proyecto_veterinaria.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloqueosFechaRequestDTO {
    
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
    
    private String descripcion;
    
    private Boolean bloqueado; // opcional, por defecto true
}

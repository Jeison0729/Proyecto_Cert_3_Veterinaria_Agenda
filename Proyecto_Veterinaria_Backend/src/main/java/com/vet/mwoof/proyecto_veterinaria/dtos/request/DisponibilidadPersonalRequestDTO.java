package com.vet.mwoof.proyecto_veterinaria.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisponibilidadPersonalRequestDTO {
    
    @NotNull(message = "El ID del personal es obligatorio")
    private Long idPersonal;
    
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
    
    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;
    
    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;
    
    private Boolean activo;
}

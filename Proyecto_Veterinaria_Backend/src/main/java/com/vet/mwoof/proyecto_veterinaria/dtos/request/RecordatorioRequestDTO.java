package com.vet.mwoof.proyecto_veterinaria.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordatorioRequestDTO {
    
    @NotNull(message = "El ID de la cita es obligatorio")
    private Long idAgenda;
    
    @NotNull(message = "El tipo es obligatorio")
    private String tipo; // PRE_CITA, POST_CITA, SEGUIMIENTO
    
    private String descripcion;
    
    @NotNull(message = "La fecha programada es obligatoria")
    private LocalDateTime fechaProgramada;
    
    private String medio; // EMAIL, SMS, WHATSAPP, LISTA
    
}

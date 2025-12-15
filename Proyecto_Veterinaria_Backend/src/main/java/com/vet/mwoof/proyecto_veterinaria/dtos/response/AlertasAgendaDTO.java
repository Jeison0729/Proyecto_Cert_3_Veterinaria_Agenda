package com.vet.mwoof.proyecto_veterinaria.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlertasAgendaDTO {
    
    // PERSONAL_SIN_CITAS, PERSONAL_SOBRECARGADO, ALERTA_CANCELACION
    private String tipo;
    private String mensaje;
    private String prioridad; // BAJA, MEDIA, ALTA
    private LocalDate fechaAlerta;
    private Long personalId;
}

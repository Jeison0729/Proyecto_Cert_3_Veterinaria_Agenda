package com.vet.mwoof.proyecto_veterinaria.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReporteAgendaDTO {
    private LocalDate fecha;
    private Integer numeroCitas;
    private Integer citasCompletadas;
    private Integer citasCanceladas;
    private Integer citasNoAsistio;
    private BigDecimal ingresoTotal;
    private Double ocupacion; // porcentaje
}

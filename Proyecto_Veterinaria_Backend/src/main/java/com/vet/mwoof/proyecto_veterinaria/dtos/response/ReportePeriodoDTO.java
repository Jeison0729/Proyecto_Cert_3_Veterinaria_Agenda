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
public class ReportePeriodoDTO {
    
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer totalCitas;
    private Integer citasCompletadas;
    private Integer citasCanceladas;
    private Integer citasNoAsistio;
    private BigDecimal ingresoTotal;
    private BigDecimal ingresoPromedioPorCita;
    private Double tasaCancelacion;
    private Double tasaNoAsistencia;
}

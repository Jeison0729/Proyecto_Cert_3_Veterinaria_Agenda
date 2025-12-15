package com.vet.mwoof.proyecto_veterinaria.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisponibilidadPersonalResumenDTO {
    
    private Long idPersonal;
    private String nombrePersonal;
    private Integer citasProgramadas;
    private Integer citasCompletadas;
    private Integer citasCanceladas;
    private BigDecimal tarifaPromedio;
    private Double porcentajeOcupacion;
    private String estado; // DISPONIBLE, CARGADO, SOBRECARGADO
}

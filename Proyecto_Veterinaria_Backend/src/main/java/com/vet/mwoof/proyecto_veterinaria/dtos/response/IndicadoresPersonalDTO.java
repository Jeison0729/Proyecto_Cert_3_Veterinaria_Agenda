package com.vet.mwoof.proyecto_veterinaria.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndicadoresPersonalDTO {
    private Long idPersonal;
    private String nombrePersonal;
    private Integer citasAsignadas;
    private Integer citasCompletadas;
    private Integer citasCanceladas;
    private Double porcentajeOcupacion;
    private String alertas;// DISPONIBLE, SIN_CITAS, SOBRECARGADO
}

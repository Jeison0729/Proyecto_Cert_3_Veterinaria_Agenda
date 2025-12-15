package com.vet.mwoof.proyecto_veterinaria.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngresoServicioCreateDTO {
    private Integer idServicio;
    private Long idPersonal;
    private Integer cantidad;
    private BigDecimal valorServicio;
    private String adicionales;
    private String observaciones;
}

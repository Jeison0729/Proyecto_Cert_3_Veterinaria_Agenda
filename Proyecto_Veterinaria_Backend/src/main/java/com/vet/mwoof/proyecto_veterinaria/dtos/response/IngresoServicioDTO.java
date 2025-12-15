package com.vet.mwoof.proyecto_veterinaria.dtos.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngresoServicioDTO {
    private Long id;
    private Long idAgenda;
    private Integer idServicio;
    private String nombreServicio;
    private Long idPersonal;
    private String nombrePersonal;
    private Integer cantidad;
    private Integer duracionMin;
    private BigDecimal valorServicio;
    private String adicionales;
    private String observaciones;
}

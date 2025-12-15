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
public class ServicioCreateRequestDTO {
    private String nombre;
    private Integer idCategoria;
    private Integer duracionMinutos;
    private BigDecimal precio;
    private String descripcion;
}

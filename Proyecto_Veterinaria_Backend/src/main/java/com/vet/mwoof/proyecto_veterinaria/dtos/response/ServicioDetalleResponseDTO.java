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
public class ServicioDetalleResponseDTO {
    private Integer idServicio;
    private String nombreServicio;
    private String personalNombre;
    private Integer cantidad;
    private Integer duracionMin;
    private BigDecimal valorServicio;
    private String adicionales;
}

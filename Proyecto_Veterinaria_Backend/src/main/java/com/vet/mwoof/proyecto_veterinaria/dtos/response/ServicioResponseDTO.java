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
public class ServicioResponseDTO {
    private Integer id;
    private String nombre;
    private Integer idCategoria;
    private String categoriaNombre;
    private Integer duracionMinutos;
    private BigDecimal precio;
    private String descripcion;
}

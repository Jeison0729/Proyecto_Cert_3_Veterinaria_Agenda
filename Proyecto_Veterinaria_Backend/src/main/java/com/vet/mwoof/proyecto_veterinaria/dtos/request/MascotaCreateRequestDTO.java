package com.vet.mwoof.proyecto_veterinaria.dtos.request;

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
public class MascotaCreateRequestDTO {
    private Long idCliente;
    private Integer idEspecie;
    private Integer idSexo;
    
    private String nombre;
    private String raza;
    private LocalDate fechaNacimiento;
    private String color;
    private Boolean esterilizado;
    private BigDecimal pesoActual;
    private String observaciones;
    private String foto;
}

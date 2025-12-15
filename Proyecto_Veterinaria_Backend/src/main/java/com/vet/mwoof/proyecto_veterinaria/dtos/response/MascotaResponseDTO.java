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
public class MascotaResponseDTO {
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private String sexo;
    private LocalDate fechaNacimiento;
    private String color;
    private Boolean esterilizado;
    private BigDecimal pesoActual;
    private String observaciones;
    private String foto;
    private Long idCliente;
    private String clienteNombreCompleto;
}

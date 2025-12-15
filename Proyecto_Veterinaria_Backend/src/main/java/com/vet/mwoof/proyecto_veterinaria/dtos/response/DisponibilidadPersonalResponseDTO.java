package com.vet.mwoof.proyecto_veterinaria.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisponibilidadPersonalResponseDTO {
    private Long id;
    private Long idPersonal;
    private String nombrePersonal;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Boolean activo;
}

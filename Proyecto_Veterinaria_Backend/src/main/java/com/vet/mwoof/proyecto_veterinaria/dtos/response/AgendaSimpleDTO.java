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
public class AgendaSimpleDTO {
    private Long id;
    private String codigo;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer duracionMin;
    private String mascota;
    private String cliente;
}

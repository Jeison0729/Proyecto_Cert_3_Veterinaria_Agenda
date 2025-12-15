package com.vet.mwoof.proyecto_veterinaria.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisponibilidadDTO {
    private LocalDate fecha;
    private List<LocalTime> horasDisponibles;
    private List<LocalTime> horasOcupadas;
}

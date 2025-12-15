package com.vet.mwoof.proyecto_veterinaria.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HorarioAtencionDTO {
    private Integer id;
    private Integer diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Boolean activo;
}

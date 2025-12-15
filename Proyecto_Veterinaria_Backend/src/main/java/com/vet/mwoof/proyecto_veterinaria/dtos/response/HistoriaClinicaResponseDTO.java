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
public class HistoriaClinicaResponseDTO {
    private Long id;
    private String codigo;
    private String mascotaNombre;
    private String personalNombre;
    private String motivoConsulta;
    private String diagnostico;
    private String tratamiento;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estadoNombre;
    private String observaciones;
    private List<ArchivoResponseDTO> archivos;
}

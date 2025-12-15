package com.vet.mwoof.proyecto_veterinaria.dtos.request;

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
public class HistoriaClinicaCreateRequestDTO {
    private Long idMascota;
    private Long idPersonal;
    private String motivoConsulta;
    private String diagnostico;
    private String tratamiento;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer idEstado;
    private String observaciones;
    private List<ArchivoRequestDTO> archivos;
}

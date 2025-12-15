package com.vet.mwoof.proyecto_veterinaria.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgendaResponseDTO {
    private Long id;
    private String codigo;
    private Long idCliente;
    private String clienteNombre;
    private Long idMascota;
    private String mascotaNombre;
    private Long idPersonal;
    private String personalNombre;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer duracionEstimadaMin;
    private BigDecimal abonoInicial;
    private BigDecimal totalCita;
    private Integer idEstado;
    private String estadoNombre;
    private Integer idMedioSolicitud;
    private String medioSolicitudNombre;
    private String observaciones;
    private LocalDateTime fechaRegistro;
    private List<IngresoServicioDTO> servicios;
    private List<RecordatorioResponseDTO> recordatorios;
}

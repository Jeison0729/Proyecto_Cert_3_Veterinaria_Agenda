package com.vet.mwoof.proyecto_veterinaria.dtos.request;

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
public class AgendaCreateRequestDTO {
    private Long idCliente;
    private Long idMascota;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer idEstado;
    private Integer idMedioSolicitud;
    private String observaciones;
    private BigDecimal abonoInicial;
    private Integer idMedioPago;
    private Integer idUsuarioRegistro;
    private List<IngresoServicioCreateDTO> servicios;
    private String recordatorioTipo;
    private LocalDateTime recordatorioFecha;
}

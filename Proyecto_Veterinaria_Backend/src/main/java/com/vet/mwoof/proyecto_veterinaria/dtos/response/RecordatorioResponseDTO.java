package com.vet.mwoof.proyecto_veterinaria.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordatorioResponseDTO {
    private Long id;
    private Long idAgenda;
    private String tipo;
    private String descripcion;
    private LocalDateTime fechaProgramada;
    private Boolean enviado;
    private LocalDateTime fechaEnvio;
    private String medio;
}

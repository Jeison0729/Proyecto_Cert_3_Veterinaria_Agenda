package com.vet.mwoof.proyecto_veterinaria.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArchivoResponseDTO {
    private Integer idTipoArchivo;
    private String tipoNombre;
    private String nombreArchivo;
    private String rutaArchivo;
    private String descripcion;
}

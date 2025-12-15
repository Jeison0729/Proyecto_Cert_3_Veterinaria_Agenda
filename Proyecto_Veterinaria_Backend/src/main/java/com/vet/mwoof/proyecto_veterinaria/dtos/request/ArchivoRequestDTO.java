package com.vet.mwoof.proyecto_veterinaria.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArchivoRequestDTO {
    private Integer idTipoArchivo;
    private String nombreArchivo;
    private String rutaArchivo;
    private String descripcion;
}

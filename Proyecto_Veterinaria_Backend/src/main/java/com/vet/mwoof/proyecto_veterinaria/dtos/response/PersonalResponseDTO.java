package com.vet.mwoof.proyecto_veterinaria.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalResponseDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private String nombreCompleto;
    private String telefono;
    private String especialidad;
    private String colorCalendario;
}

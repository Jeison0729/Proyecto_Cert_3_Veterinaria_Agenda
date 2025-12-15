package com.vet.mwoof.proyecto_veterinaria.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalCreateRequestDTO {
    private String nombres;
    private String apellidos;
    private String telefono;
    private String especialidad;
    private String colorCalendario;
}

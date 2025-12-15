package com.vet.mwoof.proyecto_veterinaria.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCreateRequestDTO {
    private String nombres;
    private String apellidos;
    private String dni;
    private String celular;
    private String celular2;
    private String email;
    private String direccion;
    private String distrito;
    private String referencia;
}

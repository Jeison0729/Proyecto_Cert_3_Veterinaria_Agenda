package com.vet.mwoof.proyecto_veterinaria.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {
    private Long id;
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

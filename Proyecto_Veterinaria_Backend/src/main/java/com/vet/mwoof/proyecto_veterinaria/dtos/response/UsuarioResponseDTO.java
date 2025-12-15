package com.vet.mwoof.proyecto_veterinaria.dtos.response;

import com.vet.mwoof.proyecto_veterinaria.entity.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private Integer id;
    private String nombre;
    private String usuario;
    private UsuarioEntity.Rol rol;
    private Boolean activo;
}

package com.vet.mwoof.proyecto_veterinaria.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ValidacionAgendaDTO {
    private Boolean valido;
    private String mensaje;
    private List<String> errores;
    private ValidacionConflictoDTO conflictos;
}

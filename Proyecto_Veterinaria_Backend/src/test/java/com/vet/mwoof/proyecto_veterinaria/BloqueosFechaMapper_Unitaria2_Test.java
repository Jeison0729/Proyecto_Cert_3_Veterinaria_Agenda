package com.vet.mwoof.proyecto_veterinaria;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.BloqueosFechaRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.mapper.BloqueosFechaMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BloqueosFechaMapperDefaultValueTest {
    @Test
    void mapper_sinBloqueado_deberiaPonerTruePorDefecto() {
        var req = BloqueosFechaRequestDTO.builder()
                .fecha(LocalDate.now())
                .descripcion("Feriado")
                .build();
        
        assertTrue(new BloqueosFechaMapper().toEntity(req).getBloqueado());
    }
}

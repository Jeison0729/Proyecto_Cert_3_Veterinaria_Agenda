package com.vet.mwoof.proyecto_veterinaria;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.BloqueosFechaRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.BloqueosFechaEntity;
import com.vet.mwoof.proyecto_veterinaria.mapper.BloqueosFechaMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BloqueosFechaMapperTest {
    @Test
    void mapper_toEntity_deberiaMapearCamposCorrectamente() {
        BloqueosFechaRequestDTO req = BloqueosFechaRequestDTO.builder()
                .fecha(LocalDate.of(2025, 12, 25))
                .descripcion("Navidad")
                .bloqueado(true)
                .build();
        
        BloqueosFechaEntity entity = new BloqueosFechaMapper().toEntity(req);
        
        assertEquals(LocalDate.of(2025, 12, 25), entity.getFecha());
        assertEquals("Navidad", entity.getDescripcion());
        assertTrue(entity.getBloqueado());
    }
}

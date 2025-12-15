package com.vet.mwoof.proyecto_veterinaria;

import com.vet.mwoof.proyecto_veterinaria.entity.BloqueosFechaEntity;
import com.vet.mwoof.proyecto_veterinaria.repository.BloqueosFechaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BloqueosFechaControllerDuplicateIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    BloqueosFechaRepository repo;
    
    @Test
    void integracion_noPermitirDuplicado_deberiaDevolver400() throws Exception {
        repo.save(BloqueosFechaEntity.builder().fecha(LocalDate.of(2026, 1, 1)).build());
        
        mockMvc.perform(post("/api/bloqueos")
                        .contentType(APPLICATION_JSON)
                        .content("{\"fecha\":\"2026-01-01\"}"))
                .andExpect(status().isBadRequest());
    }
}

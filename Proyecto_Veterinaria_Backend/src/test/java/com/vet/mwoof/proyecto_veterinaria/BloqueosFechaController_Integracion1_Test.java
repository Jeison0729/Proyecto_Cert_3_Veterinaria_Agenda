package com.vet.mwoof.proyecto_veterinaria;

import com.vet.mwoof.proyecto_veterinaria.repository.BloqueosFechaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BloqueosFechaControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    BloqueosFechaRepository repo;
    
    @Test
    void integracion_crearBloqueo_deberiaDevolver201() throws Exception {
        mockMvc.perform(post("/api/bloqueos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fecha\":\"2025-12-31\",\"descripcion\":\"Año Nuevo\"}"))
                .andExpect(status().isCreated());
    }
}

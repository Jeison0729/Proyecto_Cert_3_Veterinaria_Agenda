package com.vet.mwoof.proyecto_veterinaria;

import com.vet.mwoof.proyecto_veterinaria.controller.BloqueosFechaRestController;
import com.vet.mwoof.proyecto_veterinaria.service.BloqueosFechaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BloqueosFechaRestController.class)
class BloqueosFechaControllerBadRequestWebMvcTest {
    
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    BloqueosFechaService service;
    
    @Test
    void jsonInvalido_deberiaDevolver400() throws Exception {
        mockMvc.perform(post("/api/bloqueos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fecha\": \"2025-12-25\""))  // JSON roto a propósito con el fin de validar
                .andExpect(status().isBadRequest());
    }
}

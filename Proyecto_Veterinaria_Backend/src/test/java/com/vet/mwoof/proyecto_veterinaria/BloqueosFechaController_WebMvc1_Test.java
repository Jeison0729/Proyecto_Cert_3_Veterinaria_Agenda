package com.vet.mwoof.proyecto_veterinaria;

import com.vet.mwoof.proyecto_veterinaria.controller.BloqueosFechaRestController;
import com.vet.mwoof.proyecto_veterinaria.service.BloqueosFechaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BloqueosFechaRestController.class)
class BloqueosFechaControllerWebMvcTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    BloqueosFechaService service;
    
    @Test
    void webmvc_getBloqueos_deberiaDevolver200() throws Exception {
        mockMvc.perform(get("/api/bloqueos"))
                .andExpect(status().isOk());
    }
}

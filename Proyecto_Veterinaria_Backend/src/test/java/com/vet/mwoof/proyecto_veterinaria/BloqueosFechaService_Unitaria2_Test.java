package com.vet.mwoof.proyecto_veterinaria;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.BloqueosFechaRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.BloqueosFechaEntity;
import com.vet.mwoof.proyecto_veterinaria.repository.BloqueosFechaRepository;
import com.vet.mwoof.proyecto_veterinaria.serviceimpl.BloqueosFechaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BloqueosFechaServiceDuplicateTest {
    
    @Mock
    private BloqueosFechaRepository repo;
    
    @InjectMocks
    private BloqueosFechaServiceImpl service;
    
    @Test
    void service_crearCuandoYaExiste_deberiaLanzarExcepcion() {
        // Arrange
        LocalDate fecha = LocalDate.of(2025, 12, 25);
        BloqueosFechaRequestDTO request = BloqueosFechaRequestDTO.builder()
                .fecha(fecha)
                .build();
        
        BloqueosFechaEntity existente = new BloqueosFechaEntity();
        existente.setFecha(fecha);
        
        when(repo.findByFecha(fecha)).thenReturn(Optional.of(existente));
        
        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.crearBloqueo(request));
        
        
        assertTrue(ex.getMessage().contains("Ya existe un bloqueo para la fecha: 2025-12-25"));
    }
}

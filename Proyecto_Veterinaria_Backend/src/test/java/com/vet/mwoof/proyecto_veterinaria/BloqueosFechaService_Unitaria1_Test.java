package com.vet.mwoof.proyecto_veterinaria;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.BloqueosFechaRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.BloqueosFechaEntity;
import com.vet.mwoof.proyecto_veterinaria.mapper.BloqueosFechaMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.BloqueosFechaRepository;
import com.vet.mwoof.proyecto_veterinaria.serviceimpl.BloqueosFechaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BloqueosFechaServiceImplTest {
    @Mock
    BloqueosFechaRepository repo;
    @Mock
    BloqueosFechaMapper mapper;
    @InjectMocks
    BloqueosFechaServiceImpl service;
    
    @Test
    void service_crearCuandoNoExiste_deberiaGuardar() {
        // Arrange
        BloqueosFechaRequestDTO request = BloqueosFechaRequestDTO.builder()
                .fecha(LocalDate.of(2025, 12, 25))  // ← ponemos una fecha real
                .build();
        
        when(repo.findByFecha(any())).thenReturn(Optional.empty());
        when(mapper.toEntity(any())).thenReturn(new BloqueosFechaEntity());
        
        // Act
        service.crearBloqueo(request);
        
        // Assert
        verify(repo).save(any(BloqueosFechaEntity.class));
    }
}

package com.vet.mwoof.proyecto_veterinaria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "horario_atencion")
public class HorarioAtencionEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "dia_semana", nullable = false, unique = true)
    private Integer diaSemana; // 1=Lunes, 2=Martes, ..., 6=Sábado
    
    @Column(nullable = false)
    private LocalTime horaInicio;
    
    @Column(nullable = false)
    private LocalTime horaFin;
    
    private Boolean activo = true;
}

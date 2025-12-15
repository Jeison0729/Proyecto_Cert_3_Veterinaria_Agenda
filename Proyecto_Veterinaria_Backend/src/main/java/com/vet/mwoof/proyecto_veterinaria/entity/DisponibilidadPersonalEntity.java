package com.vet.mwoof.proyecto_veterinaria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "disponibilidad_personal")
public class DisponibilidadPersonalEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personal", nullable = false)
    private PersonalEntity personal;
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(nullable = false)
    private LocalTime horaInicio;
    
    @Column(nullable = false)
    private LocalTime horaFin;
    
    private Boolean activo = true;
}

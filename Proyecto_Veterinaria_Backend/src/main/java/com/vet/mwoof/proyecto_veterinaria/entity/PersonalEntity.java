package com.vet.mwoof.proyecto_veterinaria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "personal")
public class PersonalEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombres;
    
    @Column(nullable = false, length = 100)
    private String apellidos;
    
    @Column(length = 15)
    private String telefono;
    
    @Column(length = 100)
    private String especialidad = "Estética General";
    
    @Column(name = "color_calendario", length = 7)
    private String colorCalendario = "#3498db";
    
    @Column(name = "activo")
    private Boolean activo = true;
}

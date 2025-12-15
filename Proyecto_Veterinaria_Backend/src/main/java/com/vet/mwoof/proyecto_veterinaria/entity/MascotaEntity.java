package com.vet.mwoof.proyecto_veterinaria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "mascotas")
public class MascotaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;
    
    @Column(nullable = false, length = 80)
    private String nombre;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_especie")
    private EspecieEntity especie;
    
    @Column(length = 80)
    private String raza;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sexo")
    private SexoEntity sexo;
    
    private LocalDate fechaNacimiento;
    
    @Column(length = 50)
    private String color;
    
    @Column(nullable = false)
    private Boolean esterilizado = false;
    
    private BigDecimal pesoActual;
    
    private String observaciones;
    
    @Column(length = 255)
    private String foto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteEntity cliente;

}

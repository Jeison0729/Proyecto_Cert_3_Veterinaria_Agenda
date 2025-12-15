package com.vet.mwoof.proyecto_veterinaria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "servicios")
public class ServicioEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;
    
    @Column(nullable = false, length = 120)
    private String nombre;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaServicioEntity categoria;
    
    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;
    
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal precio;
    
    private String descripcion;
    
    @Column(nullable = false)
    private Boolean activo = true;
}

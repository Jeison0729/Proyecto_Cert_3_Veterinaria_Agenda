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
@Table(name = "ingresos_servicios")
public class IngresoServicioEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, length = 20, nullable = false)
    private String codigo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agenda", nullable = false)
    private AgendaEntity agenda;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio", nullable = false)
    private ServicioEntity servicio;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personal", nullable = false)
    private PersonalEntity personal;
    
    @Column(nullable = false)
    private Integer cantidad = 1;
    
    @Column(name = "duracion_min", nullable = false)
    private Integer duracionMin;
    
    @Column(name = "valor_servicio", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorServicio;
    
    @Column(length = 200)
    private String adicionales;
    
    @Column(length = 300)
    private String observaciones;
}

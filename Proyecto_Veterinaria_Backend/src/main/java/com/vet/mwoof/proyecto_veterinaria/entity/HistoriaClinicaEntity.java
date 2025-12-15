package com.vet.mwoof.proyecto_veterinaria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "historia_clinica")
public class HistoriaClinicaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;
    
    @Column(unique = true, length = 20)
    private String codigo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mascota", nullable = false)
    private MascotaEntity mascota;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personal")
    private PersonalEntity personal;
    
    private String motivoConsulta;
    
    private String diagnostico;
    
    private String tratamiento;
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    private LocalTime horaInicio;
    
    private LocalTime horaFin;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", nullable = false)
    private EstadoHistoriaEntity estado;
    
    private String observaciones;
    
    @OneToMany(mappedBy = "historiaClinica", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private List<HistoriaClinicaArchivoEntity> archivos = new ArrayList<>();
}

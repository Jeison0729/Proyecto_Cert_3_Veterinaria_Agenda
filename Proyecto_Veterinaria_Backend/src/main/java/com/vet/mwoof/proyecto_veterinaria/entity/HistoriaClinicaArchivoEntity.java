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
@Table(name = "historia_clinica_archivos")
public class HistoriaClinicaArchivoEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historia_clinica", nullable = false)
    private HistoriaClinicaEntity historiaClinica;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_archivo")
    private TipoArchivoEntity tipoArchivo;
    
    @Column(name = "nombre_archivo", nullable = false, length = 200)
    private String nombreArchivo;
    
    @Column(name = "ruta_archivo", nullable = false, length = 500)
    private String rutaArchivo;
    
    @Column(length = 255)
    private String descripcion;
}

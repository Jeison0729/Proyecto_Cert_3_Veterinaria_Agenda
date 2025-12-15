package com.vet.mwoof.proyecto_veterinaria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "recordatorios")
public class RecordatorioEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agenda", nullable = false)
    private AgendaEntity agenda;
    
    @Column(nullable = false)
    private String tipo; // PRE_CITA, POST_CITA
    
    private String descripcion;
    
    @Column(nullable = false)
    private LocalDateTime fechaProgramada;
    
    @Column(nullable = false)
    private Boolean enviado = false;
    
    private LocalDateTime fechaEnvio;
    
    private String medio; // LISTA, EMAIL, SMS
}

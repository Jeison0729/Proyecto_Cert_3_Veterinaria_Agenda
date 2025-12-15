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
@Table(name = "auditoria_citas")
public class AuditoriaCitasEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agenda", nullable = false)
    private AgendaEntity agenda;
    
    @Column(nullable = false)
    private String accion; // CREAR, ACTUALIZAR, CANCELAR, COMPLETAR
    
    @Column(name = "usuario_id")
    private Integer usuarioId;
    
    @Column(nullable = false)
    private LocalDateTime fechaAccion = LocalDateTime.now();
    
    private String detalles;
}

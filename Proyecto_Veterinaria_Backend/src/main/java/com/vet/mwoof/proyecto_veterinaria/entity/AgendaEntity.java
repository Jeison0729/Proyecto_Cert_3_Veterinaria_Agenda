package com.vet.mwoof.proyecto_veterinaria.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Table(name = "agenda")
public class AgendaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;
    
    @Column(unique = true, length = 20)
    private String codigo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteEntity cliente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mascota", nullable = false)
    private MascotaEntity mascota;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personal")
    private PersonalEntity personal;
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(nullable = false)
    private LocalTime hora;
    
    @Column(name = "duracion_estimada_min", nullable = false)
    private Integer duracionEstimadaMin = 0;
    
    @Column(name = "abono_inicial", precision = 8, scale = 2)
    private BigDecimal abonoInicial = BigDecimal.ZERO;
    
    @Column(name = "total_cita", precision = 8, scale = 2)
    private BigDecimal totalCita = BigDecimal.ZERO;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", nullable = false)
    private EstadoCitaEntity estado;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medio_solicitud")
    private MedioSolicitudEntity medioSolicitud;
    
    private String observaciones;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_registro", nullable = false)
    private UsuarioEntity usuarioRegistro;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();
    
    @OneToMany(mappedBy = "agenda", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<IngresoServicioEntity> servicios = new ArrayList<>();
    
    @OneToMany(mappedBy = "agenda", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private List<RecordatorioEntity> recordatorios = new ArrayList<>();
    
    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<AgendaPagoEntity> pagos = new ArrayList<>();
    
}

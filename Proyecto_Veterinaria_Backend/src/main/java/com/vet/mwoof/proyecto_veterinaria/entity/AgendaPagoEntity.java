package com.vet.mwoof.proyecto_veterinaria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "agenda_pagos")
public class AgendaPagoEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, length = 20)
    private String codigo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agenda", nullable = false)
    private AgendaEntity agenda;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medio_pago", nullable = false)
    private MedioPagoEntity medioPago;
    
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal monto;
    
    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

}

package com.vet.mwoof.proyecto_veterinaria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
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
@Table(name = "clientes")
public class ClienteEntity implements Serializable {
    
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
    
    @Column(nullable = false, unique = true, length = 8)
    private String dni;
    
    @Column(nullable = false, length = 15)
    private String celular;
    
    @Column(length = 15)
    private String celular2;
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 200)
    private String direccion;
    
    @Column(length = 80)
    private String distrito;
    
    @Column(length = 200)
    private String referencia;
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private List<MascotaEntity> mascotas = new ArrayList<>();
}

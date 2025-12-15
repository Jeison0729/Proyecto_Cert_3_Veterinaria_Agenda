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
@Table(name = "usuarios")
public class UsuarioEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, unique = true, length = 50)
    private String usuario;
    
    @Column(nullable = false, length = 50)
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private RolEntity rol;

    
    @Column(nullable = false)
    private Boolean activo = true;
    
    public enum Rol {
        admin, recepcion, groomer
    }
}

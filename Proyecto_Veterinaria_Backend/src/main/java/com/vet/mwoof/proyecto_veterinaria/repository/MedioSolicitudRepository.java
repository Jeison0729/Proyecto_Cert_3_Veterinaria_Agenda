package com.vet.mwoof.proyecto_veterinaria.repository;

import com.vet.mwoof.proyecto_veterinaria.entity.MedioSolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedioSolicitudRepository extends JpaRepository<MedioSolicitudEntity, Integer> {
    
}

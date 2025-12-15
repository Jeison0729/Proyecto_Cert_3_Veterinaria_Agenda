package com.vet.mwoof.proyecto_veterinaria.repository;

import com.vet.mwoof.proyecto_veterinaria.entity.HistoriaClinicaArchivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriaClinicaArchivoRepository extends JpaRepository<HistoriaClinicaArchivoEntity, Long> {
}

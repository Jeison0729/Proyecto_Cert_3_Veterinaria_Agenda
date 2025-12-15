package com.vet.mwoof.proyecto_veterinaria.service;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.RecordatorioRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.RecordatorioResponseDTO;

import java.util.List;

public interface RecordatorioService {
    
    RecordatorioResponseDTO crearRecordatorio(RecordatorioRequestDTO dto);
    
    void generarRecordatorioPostCita(Long idAgenda, String medio);
    
    List<RecordatorioResponseDTO> obtenerRecordatoriosPendientes();
    
    RecordatorioResponseDTO marcarEnviado(Long idRecordatorio);
    
    List<RecordatorioResponseDTO> obtenerRecordatoriosCita(Long idAgenda);
    
}

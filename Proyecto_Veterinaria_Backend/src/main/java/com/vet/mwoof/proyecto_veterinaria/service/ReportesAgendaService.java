package com.vet.mwoof.proyecto_veterinaria.service;


import com.vet.mwoof.proyecto_veterinaria.dtos.response.AlertasAgendaDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.IndicadoresPersonalDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ReporteAgendaDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ReportePeriodoDTO;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface ReportesAgendaService {
    ReporteAgendaDTO reporteDia(LocalDate fecha);
    
    ReportePeriodoDTO reportePeriodo(LocalDate fechaInicio, LocalDate fechaFin);
    
    ReportePeriodoDTO reporteSemanal(LocalDate fecha);
    
    ReportePeriodoDTO reporteMensual(YearMonth mesAnio);
    
    Map<LocalDate, Integer> citasPorDia(LocalDate fechaInicio, LocalDate fechaFin);
    
    List<IndicadoresPersonalDTO> indicadoresPersonal(LocalDate fechaInicio, LocalDate fechaFin);
    
    List<AlertasAgendaDTO> generarAlertas(LocalDate fechaInicio, LocalDate fechaFin);
    
}

package com.vet.mwoof.proyecto_veterinaria.serviceimpl;


import com.vet.mwoof.proyecto_veterinaria.dtos.response.AlertasAgendaDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.IndicadoresPersonalDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ReporteAgendaDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ReportePeriodoDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.AgendaEntity;
import com.vet.mwoof.proyecto_veterinaria.repository.AgendaRepository;
import com.vet.mwoof.proyecto_veterinaria.repository.PersonalRepository;
import com.vet.mwoof.proyecto_veterinaria.service.ReportesAgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportesAgendaServiceImpl implements ReportesAgendaService {
    
    private final AgendaRepository agendaRepository;
    private final PersonalRepository personalRepository;
    
    @Override
    public ReporteAgendaDTO reporteDia(LocalDate fecha) {
        List<AgendaEntity> citas = agendaRepository.findByFecha(fecha);
        
        long completadas = citas.stream().filter(c -> "COMPLETADA".equals(c.getEstado().getNombre())).count();
        long canceladas = citas.stream().filter(c -> "CANCELADA".equals(c.getEstado().getNombre())).count();
        long noAsistio = citas.stream().filter(c -> "NO ASISTIO".equals(c.getEstado().getNombre())).count();
        
        BigDecimal ingreso = citas.stream()
                .filter(c -> "COMPLETADA".equals(c.getEstado().getNombre()))
                .map(AgendaEntity :: getTotalCita)
                .reduce(BigDecimal.ZERO, BigDecimal :: add);
        
        double ocupacion = citas.size() > 0 ? (completadas * 100.0 / citas.size()) : 0.0;
        
        return ReporteAgendaDTO.builder()
                .fecha(fecha)
                .numeroCitas(citas.size())
                .citasCompletadas((int) completadas)
                .citasCanceladas((int) canceladas)
                .citasNoAsistio((int) noAsistio)
                .ingresoTotal(ingreso)
                .ocupacion(ocupacion)
                .build();
    }
    
    @Override
    public ReportePeriodoDTO reportePeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        List<AgendaEntity> citas = agendaRepository.findByFechaBetween(fechaInicio, fechaFin);
        
        long completadas = citas.stream().filter(c -> "COMPLETADA".equals(c.getEstado().getNombre())).count();
        long canceladas = citas.stream().filter(c -> "CANCELADA".equals(c.getEstado().getNombre())).count();
        long noAsistio = citas.stream().filter(c -> "NO ASISTIO".equals(c.getEstado().getNombre())).count();
        
        BigDecimal ingreso = citas.stream()
                .filter(c -> "COMPLETADA".equals(c.getEstado().getNombre()))
                .map(AgendaEntity :: getTotalCita)
                .reduce(BigDecimal.ZERO, BigDecimal :: add);
        
        BigDecimal promedio = citas.size() > 0
                ? ingreso.divide(BigDecimal.valueOf(citas.size()), 2, BigDecimal.ROUND_HALF_UP)
                : BigDecimal.ZERO;
        
        double tasaCancelacion = citas.size() > 0 ? (canceladas * 100.0 / citas.size()) : 0.0;
        double tasaNoAsistencia = citas.size() > 0 ? (noAsistio * 100.0 / citas.size()) : 0.0;
        
        return ReportePeriodoDTO.builder()
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .totalCitas(citas.size())
                .citasCompletadas((int) completadas)
                .citasCanceladas((int) canceladas)
                .citasNoAsistio((int) noAsistio)
                .ingresoTotal(ingreso)
                .ingresoPromedioPorCita(promedio)
                .tasaCancelacion(tasaCancelacion)
                .tasaNoAsistencia(tasaNoAsistencia)
                .build();
    }
    
    @Override
    public ReportePeriodoDTO reporteSemanal(LocalDate fecha) {
        LocalDate lunes = fecha.with(java.time.temporal.ChronoField.DAY_OF_WEEK, 1);
        LocalDate domingo = lunes.plusDays(6);
        return reportePeriodo(lunes, domingo);
    }
    
    @Override
    public ReportePeriodoDTO reporteMensual(YearMonth mesAnio) {
        LocalDate inicio = mesAnio.atDay(1);
        LocalDate fin = mesAnio.atEndOfMonth();
        return reportePeriodo(inicio, fin);
    }
    
    @Override
    public Map<LocalDate, Integer> citasPorDia(LocalDate fechaInicio, LocalDate fechaFin) {
        return agendaRepository.findAll().stream()
                .filter(c -> ! c.getFecha().isBefore(fechaInicio) && ! c.getFecha().isAfter(fechaFin))
                .collect(Collectors.groupingBy(AgendaEntity :: getFecha, Collectors.summingInt(a -> 1)));
    }
    
    @Override
    public List<IndicadoresPersonalDTO> indicadoresPersonal(LocalDate fechaInicio, LocalDate fechaFin) {
        return personalRepository.findAll().stream()
                .map(p -> {
                    List<AgendaEntity> citas = agendaRepository.findAll().stream()
                            .filter(c -> c.getPersonal() != null && c.getPersonal().getId().equals(p.getId())
                                    && ! c.getFecha().isBefore(fechaInicio)
                                    && ! c.getFecha().isAfter(fechaFin))
                            .collect(Collectors.toList());
                    
                    long completadas = citas.stream()
                            .filter(c -> "COMPLETADA".equals(c.getEstado().getNombre()))
                            .count();
                    
                    long canceladas = citas.stream()
                            .filter(c -> "CANCELADA".equals(c.getEstado().getNombre()))
                            .count();
                    
                    long asignadas = citas.size();
                    double ocupacion = asignadas > 0 ? (completadas * 100.0 / asignadas) : 0.0;
                    
                    String alertas;
                    if(asignadas == 0) alertas = "SIN_CITAS";
                    else if(ocupacion > 80) alertas = "SOBRECARGADO";
                    else alertas = "DISPONIBLE";
                    
                    return IndicadoresPersonalDTO.builder()
                            .idPersonal(p.getId())
                            .nombrePersonal(p.getNombres() + " " + p.getApellidos())
                            .citasAsignadas((int) asignadas)
                            .citasCompletadas((int) completadas)
                            .citasCanceladas((int) canceladas)
                            .porcentajeOcupacion(ocupacion)
                            .alertas(alertas)
                            .build();
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AlertasAgendaDTO> generarAlertas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<AlertasAgendaDTO> alertas = new ArrayList<>();
        
        List<IndicadoresPersonalDTO> indicadores = indicadoresPersonal(fechaInicio, fechaFin);
        
        for(IndicadoresPersonalDTO ind : indicadores) {
            if("SIN_CITAS".equals(ind.getAlertas())) {
                alertas.add(AlertasAgendaDTO.builder()
                        .tipo("PERSONAL_SIN_CITAS")
                        .mensaje("Personal " + ind.getNombrePersonal() + " sin citas")
                        .prioridad("MEDIA")
                        .fechaAlerta(LocalDate.now())
                        .personalId(ind.getIdPersonal())
                        .build());
            }
            
            if("SOBRECARGADO".equals(ind.getAlertas())) {
                alertas.add(AlertasAgendaDTO.builder()
                        .tipo("PERSONAL_SOBRECARGADO")
                        .mensaje("Personal " + ind.getNombrePersonal() + " sobrecargado")
                        .prioridad("ALTA")
                        .fechaAlerta(LocalDate.now())
                        .personalId(ind.getIdPersonal())
                        .build());
            }
        }
        
        return alertas;
    }
}

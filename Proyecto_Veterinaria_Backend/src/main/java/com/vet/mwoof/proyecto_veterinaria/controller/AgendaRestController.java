package com.vet.mwoof.proyecto_veterinaria.controller;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.AgendaCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.request.RecordatorioRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.*;
import com.vet.mwoof.proyecto_veterinaria.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/agenda")
@RequiredArgsConstructor
public class AgendaRestController {
    
    private final AgendaService agendaService;
    private final ValidacionAgendaService validacionService;
    private final DisponibilidadPersonalService disponibilidadService;
    private final RecordatorioService recordatorioService;
    private final ReportesAgendaService reportesService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<AgendaResponseDTO>> crearCita(
            @RequestBody AgendaCreateRequestDTO request
    ) {
        try {
            AgendaResponseDTO cita = agendaService.crearCita(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Cita agendada exitosamente", cita));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AgendaResponseDTO>> actualizarCita(
            @PathVariable Long id,
            @RequestBody AgendaCreateRequestDTO request
    ) {
        try {
            if(request.getIdUsuarioRegistro() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false, "idUsuarioRegistro requerido", null));
            }
            AgendaResponseDTO cita = agendaService.actualizarCita(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cita actualizada", cita));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AgendaResponseDTO>> obtenerCita(@PathVariable Long id) {
        try {
            AgendaResponseDTO cita = agendaService.obtenerCita(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cita encontrada", cita));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/periodo")
    public ResponseEntity<ApiResponse<List<AgendaResponseDTO>>> listarPeriodo(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin
    ) {
        try {
            List<AgendaResponseDTO> citas = agendaService.listarCitasPeriodo(fechaInicio, fechaFin);
            return ResponseEntity.ok(new ApiResponse<>(true, "Citas del período", citas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<ApiResponse<List<AgendaResponseDTO>>> listarCliente(
            @PathVariable Long idCliente
    ) {
        try {
            List<AgendaResponseDTO> citas = agendaService.listarCitasCliente(idCliente);
            return ResponseEntity.ok(new ApiResponse<>(true, "Citas del cliente", citas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<ApiResponse<List<AgendaResponseDTO>>> listarMascota(
            @PathVariable Long idMascota
    ) {
        try {
            List<AgendaResponseDTO> citas = agendaService.listarCitasMascota(idMascota);
            return ResponseEntity.ok(new ApiResponse<>(true, "Citas de la mascota", citas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/disponibilidad/personal/{idPersonal}")
    public ResponseEntity<ApiResponse<DisponibilidadPersonalResumenDTO>> resumenPersonal(
            @PathVariable Long idPersonal
    ) {
        try {
            DisponibilidadPersonalResumenDTO resumen =
                    disponibilidadService.obtenerResumenPersonal(idPersonal);
            return ResponseEntity.ok(new ApiResponse<>(true, "Resumen personal", resumen));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @PostMapping("/validar/conflictos")
    public ResponseEntity<ApiResponse<ValidacionConflictoDTO>> validarConflictos(
            @RequestParam LocalDate fecha,
            @RequestParam LocalTime horaInicio,
            @RequestParam LocalTime horaFin,
            @RequestParam List<Long> idsPersonal
    ) {
        try {
            ValidacionConflictoDTO resultado =
                    validacionService.validarConflictosHorario(fecha, horaInicio, horaFin, idsPersonal);
            return ResponseEntity.ok(new ApiResponse<>(true, "Validación completada", resultado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/horas-disponibles")
    public ResponseEntity<ApiResponse<DisponibilidadDTO>> horasDisponibles(
            @RequestParam Long idPersonal,
            @RequestParam LocalDate fecha
    ) {
        try {
            DisponibilidadDTO resultado = validacionService.obtenerHorasDisponibles(idPersonal, fecha);
            return ResponseEntity.ok(new ApiResponse<>(true, "Horas disponibles", resultado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @PostMapping("/recordatorio/crear")
    public ResponseEntity<ApiResponse<RecordatorioResponseDTO>> crearRecordatorio(
            @RequestBody RecordatorioRequestDTO request
    ) {
        try {
            RecordatorioResponseDTO recordatorio = recordatorioService.crearRecordatorio(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Recordatorio creado", recordatorio));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/recordatorios/pendientes")
    public ResponseEntity<ApiResponse<List<RecordatorioResponseDTO>>> obtenerRecordatoriosPendientes() {
        try {
            List<RecordatorioResponseDTO> recordatorios = recordatorioService.obtenerRecordatoriosPendientes();
            return ResponseEntity.ok(new ApiResponse<>(true, "Recordatorios pendientes", recordatorios));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @PutMapping("/recordatorio/{id}/enviar")
    public ResponseEntity<ApiResponse<RecordatorioResponseDTO>> marcarEnviado(@PathVariable Long id) {
        try {
            RecordatorioResponseDTO recordatorio = recordatorioService.marcarEnviado(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Recordatorio enviado", recordatorio));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @PutMapping("/{id}/reasignar")
    public ResponseEntity<ApiResponse<AgendaResponseDTO>> reasignarCita(
            @PathVariable Long id,
            @RequestParam LocalDate nuevaFecha,
            @RequestParam LocalTime nuevaHora
    ) {
        try {
            AgendaResponseDTO cita = agendaService.reasignarCita(id, nuevaFecha, nuevaHora);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cita reasignada", cita));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ApiResponse<AgendaResponseDTO>> cancelarCita(
            @PathVariable Long id,
            @RequestParam String motivo,
            @RequestParam(required = false) Integer usuarioId
    ) {
        try {
            AgendaResponseDTO cita = agendaService.cancelarCita(id, motivo, usuarioId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cita cancelada", cita));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @PutMapping("/{id}/completar")
    public ResponseEntity<ApiResponse<AgendaResponseDTO>> completarCita(
            @PathVariable Long id,
            @RequestParam(required = false) Integer usuarioId
    ) {
        try {
            AgendaResponseDTO cita = agendaService.completarCita(id, usuarioId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cita completada", cita));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    
    @GetMapping("/reporte/dia")
    public ResponseEntity<ApiResponse<ReporteAgendaDTO>> reporteDia(@RequestParam LocalDate fecha) {
        try {
            ReporteAgendaDTO reporte = reportesService.reporteDia(fecha);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reporte diario", reporte));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/reporte/periodo")
    public ResponseEntity<ApiResponse<ReportePeriodoDTO>> reportePeriodo(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin
    ) {
        try {
            ReportePeriodoDTO reporte = reportesService.reportePeriodo(fechaInicio, fechaFin);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reporte del período", reporte));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/reporte/personal")
    public ResponseEntity<ApiResponse<List<IndicadoresPersonalDTO>>> indicadoresPersonal(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin
    ) {
        try {
            List<IndicadoresPersonalDTO> indicadores =
                    reportesService.indicadoresPersonal(fechaInicio, fechaFin);
            return ResponseEntity.ok(new ApiResponse<>(true, "Indicadores de personal", indicadores));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
    @GetMapping("/alertas")
    public ResponseEntity<ApiResponse<List<AlertasAgendaDTO>>> obtenerAlertas(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin
    ) {
        try {
            List<AlertasAgendaDTO> alertas = reportesService.generarAlertas(fechaInicio, fechaFin);
            return ResponseEntity.ok(new ApiResponse<>(true, "Alertas de agenda", alertas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}

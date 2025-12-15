package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.service.AgendaService;
import com.vet.mwoof.proyecto_veterinaria.service.ValidacionAgendaService;
import com.vet.mwoof.proyecto_veterinaria.service.RecordatorioService;
import com.vet.mwoof.proyecto_veterinaria.dtos.request.*;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.*;
import com.vet.mwoof.proyecto_veterinaria.entity.*;
import com.vet.mwoof.proyecto_veterinaria.mapper.AgendaMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AgendaServiceImpl implements AgendaService {
    
    private final AgendaRepository agendaRepository;
    private final ClienteRepository clienteRepository;
    private final MascotaRepository mascotaRepository;
    private final ServicioRepository servicioRepository;
    private final PersonalRepository personalRepository;
    private final EstadoCitaRepository estadoCitaRepository;
    private final MedioSolicitudRepository medioSolicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final MedioPagoRepository medioPagoRepository;
    private final IngresoServicioRepository ingresoServicioRepository;
    private final AgendaPagoRepository agendaPagoRepository;
    private final RecordatorioRepository recordatorioRepository;
    private final AuditoriaCitasRepository auditoriaCitasRepository;
    
    private final ValidacionAgendaService validacionService;
    private final RecordatorioService recordatorioService;
    private final AgendaMapper agendaMapper;
    
    @Override
    public AgendaResponseDTO crearCita(AgendaCreateRequestDTO request) {
        // 1. Validar conflictos
        List<Long> idsPersonal = request.getServicios().stream()
                .map(s -> s.getIdPersonal())
                .collect(Collectors.toList());
        
        LocalTime horaFin = request.getHora().plusMinutes(30);
        ValidacionConflictoDTO validacion = validacionService
                .validarConflictosHorario(request.getFecha(), request.getHora(), horaFin, idsPersonal);
        
        if (validacion.getHayConflicto()) {
            throw new RuntimeException(validacion.getMensaje());
        }
        
        // 2. Buscar entidades
        ClienteEntity cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        MascotaEntity mascota = mascotaRepository.findById(request.getIdMascota())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        
        EstadoCitaEntity estado = estadoCitaRepository.findById(request.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        
        UsuarioEntity usuario = usuarioRepository.findById(request.getIdUsuarioRegistro())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // 3. Crear agenda
        AgendaEntity agenda = new AgendaEntity();
        agenda.setCodigo("CITA-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        agenda.setCliente(cliente);
        agenda.setMascota(mascota);
        agenda.setFecha(request.getFecha());
        agenda.setHora(request.getHora());
        agenda.setEstado(estado);
        agenda.setObservaciones(request.getObservaciones());
        agenda.setUsuarioRegistro(usuario);
        
        if (request.getIdMedioSolicitud() != null) {
            MedioSolicitudEntity medio = medioSolicitudRepository.findById(request.getIdMedioSolicitud())
                    .orElse(null);
            agenda.setMedioSolicitud(medio);
        }
        
        BigDecimal totalCita = BigDecimal.ZERO;
        int duracionTotal = 0;
        
        // 4. Agregar servicios
        for (IngresoServicioCreateDTO servDTO : request.getServicios()) {
            ServicioEntity servicio = servicioRepository.findById(servDTO.getIdServicio())
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
            
            PersonalEntity personal = personalRepository.findById(servDTO.getIdPersonal())
                    .orElseThrow(() -> new RuntimeException("Personal no encontrado"));
            
            IngresoServicioEntity ingreso = new IngresoServicioEntity();
            ingreso.setCodigo("IS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            ingreso.setAgenda(agenda);
            ingreso.setServicio(servicio);
            ingreso.setPersonal(personal);
            ingreso.setCantidad(servDTO.getCantidad());
            
            int duracionServicio = servicio.getDuracionMinutos() * servDTO.getCantidad();
            ingreso.setDuracionMin(duracionServicio);
            ingreso.setValorServicio(servDTO.getValorServicio());
            ingreso.setAdicionales(servDTO.getAdicionales());
            ingreso.setObservaciones(servDTO.getObservaciones());
            
            agenda.getServicios().add(ingreso);
            
            totalCita = totalCita.add(servDTO.getValorServicio().multiply(BigDecimal.valueOf(servDTO.getCantidad())));
            duracionTotal += duracionServicio;
        }
        
        agenda.setDuracionEstimadaMin(duracionTotal);
        agenda.setTotalCita(totalCita);
        agenda.setAbonoInicial(request.getAbonoInicial() != null ? request.getAbonoInicial() : BigDecimal.ZERO);
        
        // 5. Procesar pago
        if (request.getAbonoInicial() != null && request.getAbonoInicial().compareTo(BigDecimal.ZERO) > 0) {
            MedioPagoEntity medioPago = medioPagoRepository.findById(request.getIdMedioPago())
                    .orElseThrow(() -> new RuntimeException("Medio de pago no encontrado"));
            
            AgendaPagoEntity pago = new AgendaPagoEntity();
            pago.setCodigo("PAGO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            pago.setAgenda(agenda);
            pago.setMedioPago(medioPago);
            pago.setMonto(request.getAbonoInicial());
            pago.setUsuario(usuario);
            agenda.getPagos().add(pago);
        }
        
        // 6. Guardar
        AgendaEntity guardada = agendaRepository.save(agenda);
        
        // 7. Recordatorio
        if (request.getRecordatorioTipo() != null && request.getRecordatorioFecha() != null) {
            RecordatorioRequestDTO recordDTO = RecordatorioRequestDTO.builder()
                    .idAgenda(guardada.getId())
                    .tipo(request.getRecordatorioTipo())
                    .descripcion("Recordatorio para " + mascota.getNombre())
                    .fechaProgramada(request.getRecordatorioFecha())
                    .medio("LISTA")
                    .build();
            recordatorioService.crearRecordatorio(recordDTO);
        }
        
        // 8. Auditoría
        registrarAuditoria(guardada.getId(), "CREAR", usuario.getId(), "Cita creada");
        
        return agendaMapper.toResponseDTO(guardada);
    }
    
    @Override
    public AgendaResponseDTO actualizarCita(Long id, AgendaCreateRequestDTO request) {
        AgendaEntity agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        if (request.getIdUsuarioRegistro() == null) {
            throw new RuntimeException("idUsuarioRegistro es requerido");
        }
        
        UsuarioEntity usuario = usuarioRepository.findById(request.getIdUsuarioRegistro())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (request.getFecha() != null) agenda.setFecha(request.getFecha());
        if (request.getHora() != null) agenda.setHora(request.getHora());
        if (request.getIdEstado() != null) {
            EstadoCitaEntity estado = estadoCitaRepository.findById(request.getIdEstado())
                    .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
            agenda.setEstado(estado);
        }
        if (request.getObservaciones() != null) agenda.setObservaciones(request.getObservaciones());
        
        AgendaEntity actualizada = agendaRepository.save(agenda);
        registrarAuditoria(actualizada.getId(), "ACTUALIZAR", usuario.getId(), "Cita actualizada");
        
        return agendaMapper.toResponseDTO(actualizada);
    }
    
    @Override
    @Transactional(readOnly = true)
    public AgendaResponseDTO obtenerCita(Long id) {
        AgendaEntity agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        return agendaMapper.toResponseDTO(agenda);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AgendaResponseDTO> listarCitasPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        return agendaRepository.findByFechaBetween(fechaInicio, fechaFin).stream()
                .map(agendaMapper::toResponseDTO)
                .toList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AgendaResponseDTO> listarCitasCliente(Long idCliente) {
        return agendaRepository.findByClienteId(idCliente).stream()
                .map(agendaMapper::toResponseDTO)
                .toList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AgendaResponseDTO> listarCitasMascota(Long idMascota) {
        return agendaRepository.findByMascotaId(idMascota).stream()
                .map(agendaMapper::toResponseDTO)
                .toList();
    }
    @Override
    public AgendaResponseDTO reasignarCita(Long idCita, LocalDate nuevaFecha, LocalTime nuevaHora) {
        AgendaEntity agenda = agendaRepository.findById(idCita)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        if (!"CANCELADA".equals(agenda.getEstado().getNombre())) {
            throw new RuntimeException("Solo se pueden reasignar citas canceladas");
        }
        
        List<Long> idsPersonal = agenda.getServicios().stream()
                .map(s -> s.getPersonal().getId())
                .collect(Collectors.toList());
        
        LocalTime horaFin = nuevaHora.plusMinutes(agenda.getDuracionEstimadaMin());
        ValidacionConflictoDTO validacion = validacionService
                .validarConflictosHorario(nuevaFecha, nuevaHora, horaFin, idsPersonal);
        
        if (validacion.getHayConflicto()) {
            throw new RuntimeException(validacion.getMensaje());
        }
        
        EstadoCitaEntity pendiente = estadoCitaRepository.findAll().stream()
                .filter(e -> "PENDIENTE".equals(e.getNombre()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Estado PENDIENTE no encontrado"));
        
        agenda.setFecha(nuevaFecha);
        agenda.setHora(nuevaHora);
        agenda.setEstado(pendiente);
        
        AgendaEntity reasignada = agendaRepository.save(agenda);
        registrarAuditoria(reasignada.getId(), "REASIGNAR", null,
                "Reasignada a " + nuevaFecha + " " + nuevaHora);
        
        return agendaMapper.toResponseDTO(reasignada);
    }
    
    @Override
    public AgendaResponseDTO cancelarCita(Long id, String motivo, Integer usuarioId) {
        AgendaEntity agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        EstadoCitaEntity cancelada = estadoCitaRepository.findAll().stream()
                .filter(e -> "CANCELADA".equals(e.getNombre()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Estado CANCELADA no encontrado"));
        
        agenda.setEstado(cancelada);
        String obs = (agenda.getObservaciones() != null ? agenda.getObservaciones() : "")
                + "\n[CANCELADA] " + motivo;
        agenda.setObservaciones(obs);
        
        AgendaEntity actualizada = agendaRepository.save(agenda);
        registrarAuditoria(actualizada.getId(), "CANCELAR", usuarioId,
                "Cancelada: " + motivo);
        
        return agendaMapper.toResponseDTO(actualizada);
    }
    
    @Override
    public AgendaResponseDTO completarCita(Long id, Integer usuarioId) {
        AgendaEntity agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        EstadoCitaEntity completada = estadoCitaRepository.findAll().stream()
                .filter(e -> "COMPLETADA".equals(e.getNombre()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Estado COMPLETADA no encontrado"));
        
        agenda.setEstado(completada);
        AgendaEntity actualizada = agendaRepository.save(agenda);
        
        registrarAuditoria(actualizada.getId(), "COMPLETAR", usuarioId, "Cita completada");
        recordatorioService.generarRecordatorioPostCita(actualizada.getId(), "LISTA");
        
        return agendaMapper.toResponseDTO(actualizada);
    }
    
    private void registrarAuditoria(Long idAgenda, String accion, Integer usuarioId, String detalles) {
        AuditoriaCitasEntity auditoria = AuditoriaCitasEntity.builder()
                .agenda(agendaRepository.findById(idAgenda).orElse(null))
                .accion(accion)
                .usuarioId(usuarioId)
                .fechaAccion(LocalDateTime.now())
                .detalles(detalles)
                .build();
        
        auditoriaCitasRepository.save(auditoria);
    }
}

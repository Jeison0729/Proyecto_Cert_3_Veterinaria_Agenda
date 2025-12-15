package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.MascotaCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.MascotaResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.ClienteEntity;
import com.vet.mwoof.proyecto_veterinaria.entity.EspecieEntity;
import com.vet.mwoof.proyecto_veterinaria.entity.MascotaEntity;
import com.vet.mwoof.proyecto_veterinaria.entity.SexoEntity;
import com.vet.mwoof.proyecto_veterinaria.mapper.MascotaMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.ClienteRepository;
import com.vet.mwoof.proyecto_veterinaria.repository.EspecieRepository;
import com.vet.mwoof.proyecto_veterinaria.repository.MascotaRepository;
import com.vet.mwoof.proyecto_veterinaria.repository.SexoRepository;
import com.vet.mwoof.proyecto_veterinaria.service.MascotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements MascotaService {
    
    private final MascotaRepository mascotaRepository;
    private final ClienteRepository clienteRepository;
    private final EspecieRepository especieRepository;
    private final SexoRepository sexoRepository;
    private final MascotaMapper mascotaMapper;
    
    @Override
    @Transactional
    public MascotaResponseDTO crear(MascotaCreateRequestDTO request) {
        ClienteEntity cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        MascotaEntity entity = mascotaMapper.toEntity(request);
        entity.setCliente(cliente);
        
        EspecieEntity especie = especieRepository.findById(request.getIdEspecie()).orElseThrow();
        entity.setEspecie(especie);
        
        SexoEntity sexo = sexoRepository.findById(request.getIdSexo()).orElseThrow();
        entity.setSexo(sexo);
        
        MascotaEntity saved = mascotaRepository.save(entity);
        
        String clienteNombre = cliente.getNombres() + " " + cliente.getApellidos();
        return mascotaMapper.toResponse(saved, clienteNombre);
    }
    
    @Override
    @Transactional(readOnly = true)
    public MascotaResponseDTO obtenerPorId(Long id) {
        MascotaEntity entity = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        
        String clienteNombre = entity.getCliente().getNombres() + " " + entity.getCliente().getApellidos();
        return mascotaMapper.toResponse(entity, clienteNombre);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MascotaResponseDTO> listarTodas() {
        return mascotaRepository.findAll().stream()
                .map(entity -> {
                    String clienteNombre = entity.getCliente().getNombres() + " " + entity.getCliente().getApellidos();
                    return mascotaMapper.toResponse(entity, clienteNombre);
                })
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MascotaResponseDTO> listarPorCliente(Long idCliente) {
        
        return mascotaRepository.findByClienteId(idCliente).stream()
                .map(entity -> {
                    String clienteNombre = entity.getCliente().getNombres() + " " + entity.getCliente().getApellidos();
                    return mascotaMapper.toResponse(entity, clienteNombre);
                })
                .collect(Collectors.toList());
    }
    
    
    @Override
    @Transactional
    public MascotaResponseDTO actualizar(Long id, MascotaCreateRequestDTO request) {
        MascotaEntity entity = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        
        EspecieEntity especie = especieRepository.findById(request.getIdEspecie()).orElseThrow();
        
        SexoEntity sexo = sexoRepository.findById(request.getIdSexo()).orElseThrow();
        
        entity.setEspecie(especie);
        entity.setSexo(sexo);
        
        entity.setNombre(request.getNombre());
        entity.setRaza(request.getRaza());
        entity.setFechaNacimiento(request.getFechaNacimiento());
        entity.setColor(request.getColor());
        entity.setEsterilizado(request.getEsterilizado());
        entity.setPesoActual(request.getPesoActual());
        entity.setObservaciones(request.getObservaciones());
        entity.setFoto(request.getFoto());
        
        if(request.getIdCliente() != null) {
            ClienteEntity nuevoCliente = clienteRepository.findById(request.getIdCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            entity.setCliente(nuevoCliente);
        }
        
        MascotaEntity updated = mascotaRepository.save(entity);
        
        String clienteNombre = updated.getCliente().getNombres() + " " + updated.getCliente().getApellidos();
        return mascotaMapper.toResponse(updated, clienteNombre);
    }
    
    @Override
    @Transactional
    public void eliminar(Long id) {
        if(! mascotaRepository.existsById(id)) {
            throw new RuntimeException("Mascota no encontrada");
        }
        mascotaRepository.deleteById(id);
    }
}

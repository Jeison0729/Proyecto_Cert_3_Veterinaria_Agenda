package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.ClienteCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.ClienteResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.ClienteEntity;
import com.vet.mwoof.proyecto_veterinaria.mapper.ClienteMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.ClienteRepository;
import com.vet.mwoof.proyecto_veterinaria.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    
    @Override
    @Transactional
    public ClienteResponseDTO crear(ClienteCreateRequestDTO request) {
        ClienteEntity entity = clienteMapper.toEntity(request);
        ClienteEntity saved = clienteRepository.save(entity);
        return clienteMapper.toResponse(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerPorId(Long id) {
        ClienteEntity entity = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return clienteMapper.toResponse(entity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper :: toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public ClienteResponseDTO actualizar(Long id, ClienteCreateRequestDTO request) {
        ClienteEntity entity = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        entity.setNombres(request.getNombres());
        entity.setApellidos(request.getApellidos());
        entity.setDni(request.getDni());
        entity.setCelular(request.getCelular());
        entity.setCelular2(request.getCelular2());
        entity.setEmail(request.getEmail());
        entity.setDireccion(request.getDireccion());
        entity.setDistrito(request.getDistrito());
        entity.setReferencia(request.getReferencia());
        ClienteEntity updated = clienteRepository.save(entity);
        return clienteMapper.toResponse(updated);
    }
    
    @Override
    @Transactional
    public void eliminar(Long id) {
        if(! clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        clienteRepository.deleteById(id);
    }
}

package com.vet.mwoof.proyecto_veterinaria.serviceimpl;

import com.vet.mwoof.proyecto_veterinaria.dtos.request.PersonalCreateRequestDTO;
import com.vet.mwoof.proyecto_veterinaria.dtos.response.PersonalResponseDTO;
import com.vet.mwoof.proyecto_veterinaria.entity.PersonalEntity;
import com.vet.mwoof.proyecto_veterinaria.mapper.PersonalMapper;
import com.vet.mwoof.proyecto_veterinaria.repository.PersonalRepository;
import com.vet.mwoof.proyecto_veterinaria.service.PersonalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalServiceImpl implements PersonalService {
    
    private final PersonalRepository personalRepository;
    private final PersonalMapper personalMapper;
    
    @Override
    @Transactional
    public PersonalResponseDTO crear(PersonalCreateRequestDTO request) {
        PersonalEntity entity = personalMapper.toEntity(request);
        PersonalEntity saved = personalRepository.save(entity);
        return personalMapper.toResponse(saved);
    }
    
    @Override
    @Transactional
    public PersonalResponseDTO obtenerPorId(Long id) {
        PersonalEntity entity = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));
        return personalMapper.toResponse(entity);
    }
    
    @Override
    @Transactional
    public List<PersonalResponseDTO> listarTodos() {
        return personalRepository.findAll().stream()
                .map(personalMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public PersonalResponseDTO actualizar(Long id, PersonalCreateRequestDTO request) {
        PersonalEntity entity = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));
        
        entity.setNombres(request.getNombres());
        entity.setApellidos(request.getApellidos());
        entity.setTelefono(request.getTelefono());
        entity.setEspecialidad(request.getEspecialidad());
        entity.setColorCalendario(request.getColorCalendario());
        
        PersonalEntity updated = personalRepository.save(entity);
        return personalMapper.toResponse(updated);
    }
    
    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!personalRepository.existsById(id)) {
            throw new RuntimeException("Personal no encontrado");
        }
        personalRepository.deleteById(id);
    }
}

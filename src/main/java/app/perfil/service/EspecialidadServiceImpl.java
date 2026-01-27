package app.perfil.service;

import app.common.exception.DuplicateResourceException;
import app.common.exception.InvalidDataException;
import app.common.exception.ResourceNotFoundException;
import app.perfil.dto.EspecialidadDTO;
import app.perfil.dto.EspecialidadResponseDTO;
import app.perfil.entity.EspecialidadEntity;
import app.perfil.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecialidadServiceImpl implements EspecialidadService {
    
    @Autowired
    private EspecialidadRepository especialidadRepository;
    
    @Override
    @Transactional
    public EspecialidadResponseDTO create(EspecialidadDTO dto) {
        validateData(dto);
        
        if (especialidadRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new DuplicateResourceException("Especialidad", "nombre", dto.getNombre());
        }
        
        EspecialidadEntity especialidad = new EspecialidadEntity();
        especialidad.setNombre(dto.getNombre());
        especialidad.setDescripcion(dto.getDescripcion());
        especialidad.setOrigen("USUARIO");
        especialidad.setEstado(dto.getEstado());
        especialidad.setFechaCreacion(LocalDateTime.now());
        
        especialidad = especialidadRepository.save(especialidad);
        return toResponseDTO(especialidad);
    }
    
    @Override
    @Transactional
    public EspecialidadResponseDTO update(Integer id, EspecialidadDTO dto) {
        EspecialidadEntity especialidad = especialidadRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Especialidad", "id", id));
        
        validateData(dto);
        
        if (!especialidad.getNombre().equalsIgnoreCase(dto.getNombre())) {
            if (especialidadRepository.existsByNombreIgnoreCase(dto.getNombre())) {
                throw new DuplicateResourceException("Especialidad", "nombre", dto.getNombre());
            }
        }
        
        especialidad.setNombre(dto.getNombre());
        especialidad.setDescripcion(dto.getDescripcion());
        especialidad.setEstado(dto.getEstado());
        
        especialidad = especialidadRepository.save(especialidad);
        return toResponseDTO(especialidad);
    }
    
    @Override
    @Transactional(readOnly = true)
    public EspecialidadResponseDTO findById(Integer id) {
        EspecialidadEntity especialidad = especialidadRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Especialidad", "id", id));
        return toResponseDTO(especialidad);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EspecialidadResponseDTO> findAll() {
        return especialidadRepository.findAll().stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EspecialidadResponseDTO> findAll(int estado) {
        return especialidadRepository.findAll(estado, "").stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public EspecialidadResponseDTO changeStatus(Integer id) {
        EspecialidadEntity especialidad = especialidadRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Especialidad", "id", id));
        
        especialidadRepository.updateStatus(id);
        
        especialidad = especialidadRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Especialidad", "id", id));
        
        return toResponseDTO(especialidad);
    }
    
    private void validateData(EspecialidadDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new InvalidDataException("El nombre de la especialidad es requerido");
        }
        if (dto.getEstado() == null || (dto.getEstado() != 0 && dto.getEstado() != 1)) {
            throw new InvalidDataException("El estado debe ser 0 o 1");
        }
    }
    
    private EspecialidadResponseDTO toResponseDTO(EspecialidadEntity entity) {
        return new EspecialidadResponseDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getDescripcion(),
            entity.getOrigen(),
            entity.getEstado(),
            entity.getFechaCreacion()
        );
    }
}

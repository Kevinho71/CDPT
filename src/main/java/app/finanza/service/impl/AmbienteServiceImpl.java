package app.finanza.service.impl;

import app.common.exception.ResourceNotFoundException;
import app.finanza.dto.AmbienteCreateDTO;
import app.finanza.dto.AmbienteResponseDTO;
import app.finanza.dto.AmbienteUpdateDTO;
import app.finanza.entity.AmbienteEntity;
import app.finanza.repository.AmbienteRepository;
import app.finanza.service.AmbienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AmbienteServiceImpl implements AmbienteService {
    
    @Autowired
    private AmbienteRepository repository;
    
    @Override
    @Transactional(readOnly = true)
    public List<AmbienteResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AmbienteResponseDTO> findByEstado(Integer estado) {
        return repository.findByEstado(estado).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public AmbienteResponseDTO findById(Integer id) {
        AmbienteEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ambiente no encontrado con ID: " + id));
        return toResponseDTO(entity);
    }
    
    @Override
    public AmbienteResponseDTO create(AmbienteCreateDTO dto) {
        AmbienteEntity entity = new AmbienteEntity();
        
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecioHora(dto.getPrecioHora());
        entity.setEstado(1); // Activo por defecto
        
        AmbienteEntity saved = repository.save(entity);
        return toResponseDTO(saved);
    }
    
    @Override
    public AmbienteResponseDTO update(Integer id, AmbienteUpdateDTO dto) {
        AmbienteEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ambiente no encontrado con ID: " + id));
        
        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());
        if (dto.getPrecioHora() != null) entity.setPrecioHora(dto.getPrecioHora());
        if (dto.getEstado() != null) entity.setEstado(dto.getEstado());
        
        AmbienteEntity updated = repository.save(entity);
        return toResponseDTO(updated);
    }
    
    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ambiente no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }
    
    private AmbienteResponseDTO toResponseDTO(AmbienteEntity entity) {
        AmbienteResponseDTO dto = new AmbienteResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrecioHora(entity.getPrecioHora());
        dto.setEstado(entity.getEstado());
        return dto;
    }
}

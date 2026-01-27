package app.perfil.service;

import app.common.exception.DuplicateResourceException;
import app.common.exception.InvalidDataException;
import app.common.exception.ResourceNotFoundException;
import app.perfil.dto.IdiomaDTO;
import app.perfil.dto.IdiomaResponseDTO;
import app.perfil.entity.IdiomaEntity;
import app.perfil.repository.IdiomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IdiomaServiceImpl implements IdiomaService {
    
    @Autowired
    private IdiomaRepository idiomaRepository;
    
    @Override
    @Transactional
    public IdiomaResponseDTO create(IdiomaDTO dto) {
        validateData(dto);
        
        if (idiomaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new DuplicateResourceException("Idioma", "nombre", dto.getNombre());
        }
        
        IdiomaEntity idioma = new IdiomaEntity();
        idioma.setNombre(dto.getNombre());
        idioma.setEstado(dto.getEstado());
        idioma.setFechaCreacion(LocalDateTime.now());
        
        idioma = idiomaRepository.save(idioma);
        return toResponseDTO(idioma);
    }
    
    @Override
    @Transactional
    public IdiomaResponseDTO update(Integer id, IdiomaDTO dto) {
        IdiomaEntity idioma = idiomaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Idioma", "id", id));
        
        validateData(dto);
        
        if (!idioma.getNombre().equalsIgnoreCase(dto.getNombre())) {
            if (idiomaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
                throw new DuplicateResourceException("Idioma", "nombre", dto.getNombre());
            }
        }
        
        idioma.setNombre(dto.getNombre());
        idioma.setEstado(dto.getEstado());
        
        idioma = idiomaRepository.save(idioma);
        return toResponseDTO(idioma);
    }
    
    @Override
    @Transactional(readOnly = true)
    public IdiomaResponseDTO findById(Integer id) {
        IdiomaEntity idioma = idiomaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Idioma", "id", id));
        return toResponseDTO(idioma);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<IdiomaResponseDTO> findAll() {
        return idiomaRepository.findAll().stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<IdiomaResponseDTO> findAll(int estado) {
        return idiomaRepository.findAll(estado, "").stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public IdiomaResponseDTO changeStatus(Integer id) {
        IdiomaEntity idioma = idiomaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Idioma", "id", id));
        
        idiomaRepository.updateStatus(id);
        
        idioma = idiomaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Idioma", "id", id));
        
        return toResponseDTO(idioma);
    }
    
    private void validateData(IdiomaDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new InvalidDataException("El nombre del idioma es requerido");
        }
        if (dto.getEstado() == null || (dto.getEstado() != 0 && dto.getEstado() != 1)) {
            throw new InvalidDataException("El estado debe ser 0 o 1");
        }
    }
    
    private IdiomaResponseDTO toResponseDTO(IdiomaEntity entity) {
        return new IdiomaResponseDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getEstado(),
            entity.getFechaCreacion()
        );
    }
}

package app.core.service;

import app.common.exception.DuplicateResourceException;
import app.common.exception.InvalidDataException;
import app.common.exception.ResourceNotFoundException;
import app.core.dto.ProfesionDTO;
import app.core.dto.ProfesionResponseDTO;
import app.core.entity.ProfesionEntity;
import app.core.repository.ProfesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfesionServiceImpl implements ProfesionService {
    
    @Autowired
    private ProfesionRepository profesionRepository;
    
    @Override
    @Transactional
    public ProfesionResponseDTO create(ProfesionDTO dto) {
        // 1. Validar datos
        validateProfesionData(dto);
        
        // 2. Verificar duplicado por nombre
        List<ProfesionEntity> existing = profesionRepository.findAll(dto.getNombre(), -1);
        if (!existing.isEmpty()) {
            throw new DuplicateResourceException("Profesion", "nombre", dto.getNombre());
        }
        
        // 3. Crear entidad
        ProfesionEntity profesion = new ProfesionEntity();
        profesion.setId(profesionRepository.getIdPrimaryKey());
        profesion.setNombre(dto.getNombre());
        profesion.setEstado(dto.getEstado());
        
        // 4. Guardar
        profesion = profesionRepository.save(profesion);
        
        // 5. Retornar DTO
        return toResponseDTO(profesion);
    }
    
    @Override
    @Transactional
    public ProfesionResponseDTO update(Integer id, ProfesionDTO dto) {
        // 1. Buscar profesión existente
        ProfesionEntity profesion = profesionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Profesion", "id", id));
        
        // 2. Validar datos
        validateProfesionData(dto);
        
        // 3. Verificar duplicado por nombre (solo si cambió)
        if (!profesion.getNombre().equalsIgnoreCase(dto.getNombre())) {
            List<ProfesionEntity> existing = profesionRepository.findAll(dto.getNombre(), -1);
            if (!existing.isEmpty()) {
                throw new DuplicateResourceException("Profesion", "nombre", dto.getNombre());
            }
        }
        
        // 4. Actualizar
        profesion.setNombre(dto.getNombre());
        profesion.setEstado(dto.getEstado());
        
        // 5. Guardar y retornar
        profesion = profesionRepository.save(profesion);
        return toResponseDTO(profesion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ProfesionResponseDTO findById(Integer id) {
        ProfesionEntity profesion = profesionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Profesion", "id", id));
        return toResponseDTO(profesion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProfesionResponseDTO> findAll() {
        return profesionRepository.findAll().stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProfesionResponseDTO> findAll(int estado) {
        return profesionRepository.findAll("", estado).stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public ProfesionResponseDTO changeStatus(Integer id) {
        ProfesionEntity profesion = profesionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Profesion", "id", id));
        
        int currentStatus = profesion.getEstado();
        profesionRepository.updateStatus(currentStatus, id);
        
        // Recargar para obtener el nuevo estado
        profesion = profesionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Profesion", "id", id));
        
        return toResponseDTO(profesion);
    }
    
    /**
     * Valida los datos de profesión
     */
    private void validateProfesionData(ProfesionDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new InvalidDataException("El nombre de la profesión es requerido");
        }
        if (dto.getEstado() == null) {
            throw new InvalidDataException("El estado es requerido");
        }
        if (dto.getEstado() != 0 && dto.getEstado() != 1) {
            throw new InvalidDataException("El estado debe ser 0 o 1");
        }
    }
    
    /**
     * Convierte entidad a DTO de respuesta
     */
    private ProfesionResponseDTO toResponseDTO(ProfesionEntity entity) {
        return new ProfesionResponseDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getEstado()
        );
    }
}

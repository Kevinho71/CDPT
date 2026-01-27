package app.core.service;

import app.common.exception.DuplicateResourceException;
import app.common.exception.InvalidDataException;
import app.common.exception.ResourceNotFoundException;
import app.core.dto.InstitucionDTO;
import app.core.dto.InstitucionResponseDTO;
import app.core.entity.InstitucionEntity;
import app.core.entity.ProvinciaEntity;
import app.core.repository.InstitucionRepository;
import app.core.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstitucionServiceImpl implements InstitucionService {
    
    @Autowired
    private InstitucionRepository institucionRepository;
    
    @Autowired
    private ProvinciaRepository provinciaRepository;
    
    @Override
    @Transactional
    public InstitucionResponseDTO create(InstitucionDTO dto) {
        // 1. Validar datos
        validateInstitucionData(dto);
        
        // 2. Verificar que la provincia existe
        ProvinciaEntity provincia = provinciaRepository.findById(dto.getProvinciaId())
            .orElseThrow(() -> new ResourceNotFoundException("Provincia", "id", dto.getProvinciaId()));
        
        // 3. Verificar duplicado por NIT
        List<InstitucionEntity> existing = institucionRepository.findAll(dto.getNit(), -1);
        if (!existing.isEmpty()) {
            throw new DuplicateResourceException("Institucion", "NIT", dto.getNit());
        }
        
        // 4. Crear entidad
        InstitucionEntity institucion = new InstitucionEntity();
        institucion.setId(institucionRepository.getIdPrimaryKey());
        institucion.setNit(dto.getNit());
        institucion.setCompania(dto.getCompania());
        institucion.setInstitucion(dto.getInstitucion());
        institucion.setRepresentante(dto.getRepresentante());
        institucion.setCorreo(dto.getCorreo());
        institucion.setDireccion(dto.getDireccion());
        institucion.setTelefono(dto.getTelefono());
        institucion.setFax(dto.getFax());
        institucion.setHost(dto.getHost());
        institucion.setPort(dto.getPort());
        institucion.setEstado(dto.getEstado());
        institucion.setProvincia(provincia);
        
        // 5. Guardar
        institucion = institucionRepository.save(institucion);
        
        // 6. Retornar DTO
        return toResponseDTO(institucion);
    }
    
    @Override
    @Transactional
    public InstitucionResponseDTO update(Integer id, InstitucionDTO dto) {
        // 1. Buscar institución existente
        InstitucionEntity institucion = institucionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Institucion", "id", id));
        
        // 2. Validar datos
        validateInstitucionData(dto);
        
        // 3. Verificar que la provincia existe
        ProvinciaEntity provincia = provinciaRepository.findById(dto.getProvinciaId())
            .orElseThrow(() -> new ResourceNotFoundException("Provincia", "id", dto.getProvinciaId()));
        
        // 4. Verificar duplicado por NIT (solo si cambió)
        if (!institucion.getNit().equalsIgnoreCase(dto.getNit())) {
            List<InstitucionEntity> existing = institucionRepository.findAll(dto.getNit(), -1);
            if (!existing.isEmpty()) {
                throw new DuplicateResourceException("Institucion", "NIT", dto.getNit());
            }
        }
        
        // 5. Actualizar
        institucion.setNit(dto.getNit());
        institucion.setCompania(dto.getCompania());
        institucion.setInstitucion(dto.getInstitucion());
        institucion.setRepresentante(dto.getRepresentante());
        institucion.setCorreo(dto.getCorreo());
        institucion.setDireccion(dto.getDireccion());
        institucion.setTelefono(dto.getTelefono());
        institucion.setFax(dto.getFax());
        institucion.setHost(dto.getHost());
        institucion.setPort(dto.getPort());
        institucion.setEstado(dto.getEstado());
        institucion.setProvincia(provincia);
        
        // 6. Guardar y retornar
        institucion = institucionRepository.save(institucion);
        return toResponseDTO(institucion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public InstitucionResponseDTO findById(Integer id) {
        InstitucionEntity institucion = institucionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Institucion", "id", id));
        return toResponseDTO(institucion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InstitucionResponseDTO> findAll() {
        return institucionRepository.findAll().stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InstitucionResponseDTO> findAll(int estado) {
        return institucionRepository.findAll("", estado).stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public InstitucionResponseDTO changeStatus(Integer id) {
        InstitucionEntity institucion = institucionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Institucion", "id", id));
        
        int currentStatus = institucion.getEstado();
        institucionRepository.updateStatus(currentStatus, id);
        
        // Recargar para obtener el nuevo estado
        institucion = institucionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Institucion", "id", id));
        
        return toResponseDTO(institucion);
    }
    
    /**
     * Valida los datos de institución
     */
    private void validateInstitucionData(InstitucionDTO dto) {
        if (dto.getNit() == null || dto.getNit().trim().isEmpty()) {
            throw new InvalidDataException("El NIT es requerido");
        }
        if (dto.getCompania() == null || dto.getCompania().trim().isEmpty()) {
            throw new InvalidDataException("La compañía es requerida");
        }
        if (dto.getInstitucion() == null || dto.getInstitucion().trim().isEmpty()) {
            throw new InvalidDataException("El nombre de la institución es requerido");
        }
        if (dto.getEstado() == null) {
            throw new InvalidDataException("El estado es requerido");
        }
        if (dto.getEstado() != 0 && dto.getEstado() != 1) {
            throw new InvalidDataException("El estado debe ser 0 o 1");
        }
        if (dto.getProvinciaId() == null) {
            throw new InvalidDataException("La provincia es requerida");
        }
    }
    
    /**
     * Convierte entidad a DTO de respuesta
     */
    private InstitucionResponseDTO toResponseDTO(InstitucionEntity entity) {
        return new InstitucionResponseDTO(
            entity.getId(),
            entity.getNit(),
            entity.getCompania(),
            entity.getInstitucion(),
            entity.getRepresentante(),
            entity.getCorreo(),
            entity.getDireccion(),
            entity.getTelefono(),
            entity.getFax(),
            entity.getHost(),
            entity.getPort(),
            entity.getEstado(),
            entity.getProvincia().getId(),
            entity.getProvincia().getNombre()
        );
    }
}

package app.perfil.service;

import app.common.exception.DuplicateResourceException;
import app.common.exception.InvalidDataException;
import app.common.exception.ResourceNotFoundException;
import app.perfil.dto.SectorDTO;
import app.perfil.dto.SectorResponseDTO;
import app.perfil.entity.SectorEntity;
import app.perfil.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectorServiceImpl implements SectorService {
    
    @Autowired
    private SectorRepository sectorRepository;
    
    @Override
    @Transactional
    public SectorResponseDTO create(SectorDTO dto) {
        validateData(dto);
        
        if (sectorRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new DuplicateResourceException("Sector", "nombre", dto.getNombre());
        }
        
        SectorEntity sector = new SectorEntity();
        sector.setNombre(dto.getNombre());
        sector.setDescripcion(dto.getDescripcion());
        sector.setIcono(dto.getIcono());
        sector.setOrigen("USUARIO");
        sector.setEstado(dto.getEstado());
        sector.setFechaCreacion(LocalDateTime.now());
        
        sector = sectorRepository.save(sector);
        return toResponseDTO(sector);
    }
    
    @Override
    @Transactional
    public SectorResponseDTO update(Integer id, SectorDTO dto) {
        SectorEntity sector = sectorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sector", "id", id));
        
        validateData(dto);
        
        if (!sector.getNombre().equalsIgnoreCase(dto.getNombre())) {
            if (sectorRepository.existsByNombreIgnoreCase(dto.getNombre())) {
                throw new DuplicateResourceException("Sector", "nombre", dto.getNombre());
            }
        }
        
        sector.setNombre(dto.getNombre());
        sector.setDescripcion(dto.getDescripcion());
        sector.setIcono(dto.getIcono());
        sector.setEstado(dto.getEstado());
        
        sector = sectorRepository.save(sector);
        return toResponseDTO(sector);
    }
    
    @Override
    @Transactional(readOnly = true)
    public SectorResponseDTO findById(Integer id) {
        SectorEntity sector = sectorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sector", "id", id));
        return toResponseDTO(sector);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SectorResponseDTO> findAll() {
        return sectorRepository.findAll().stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SectorResponseDTO> findAll(int estado) {
        return sectorRepository.findAll(estado, "").stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public SectorResponseDTO changeStatus(Integer id) {
        SectorEntity sector = sectorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sector", "id", id));
        
        sectorRepository.updateStatus(id);
        
        sector = sectorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sector", "id", id));
        
        return toResponseDTO(sector);
    }
    
    private void validateData(SectorDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new InvalidDataException("El nombre del sector es requerido");
        }
        if (dto.getEstado() == null || (dto.getEstado() != 0 && dto.getEstado() != 1)) {
            throw new InvalidDataException("El estado debe ser 0 o 1");
        }
    }
    
    private SectorResponseDTO toResponseDTO(SectorEntity entity) {
        return new SectorResponseDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getDescripcion(),
            entity.getIcono(),
            entity.getOrigen(),
            entity.getEstado(),
            entity.getFechaCreacion()
        );
    }
}

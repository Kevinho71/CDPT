package app.cms.service.impl;

import app.cms.dto.EstadisticasPublicasCreateDTO;
import app.cms.dto.EstadisticasPublicasResponseDTO;
import app.cms.dto.EstadisticasPublicasUpdateDTO;
import app.cms.entity.EstadisticasPublicasEntity;
import app.cms.repository.EstadisticasPublicasRepository;
import app.cms.service.EstadisticasPublicasService;
import app.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstadisticasPublicasServiceImpl implements EstadisticasPublicasService {
    
    @Autowired
    private EstadisticasPublicasRepository repository;
    
    @Override
    @Transactional(readOnly = true)
    public List<EstadisticasPublicasResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EstadisticasPublicasResponseDTO> findVisible() {
        return repository.findByVisibleTrue().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EstadisticasPublicasResponseDTO> findVisibleOrdenadas() {
        return repository.findByVisibleTrueOrderByOrdenAsc().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public EstadisticasPublicasResponseDTO findById(Integer id) {
        EstadisticasPublicasEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estadística no encontrada con ID: " + id));
        return toResponseDTO(entity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public EstadisticasPublicasResponseDTO findByClave(String clave) {
        EstadisticasPublicasEntity entity = repository.findByClave(clave)
                .orElseThrow(() -> new ResourceNotFoundException("Estadística no encontrada con clave: " + clave));
        return toResponseDTO(entity);
    }
    
    @Override
    public EstadisticasPublicasResponseDTO create(EstadisticasPublicasCreateDTO dto) {
        EstadisticasPublicasEntity entity = new EstadisticasPublicasEntity();
        
        entity.setClave(dto.getClave());
        entity.setTitulo(dto.getTitulo());
        entity.setValor(dto.getValor());
        entity.setIcono(dto.getIcono());
        entity.setDescripcion(dto.getDescripcion());
        entity.setOrden(dto.getOrden());
        entity.setVisible(dto.getVisible() != null ? dto.getVisible() : true);
        entity.setEstado(1); // Activo por defecto
        entity.setFechaActualizacion(LocalDateTime.now());
        
        EstadisticasPublicasEntity saved = repository.save(entity);
        return toResponseDTO(saved);
    }
    
    @Override
    public EstadisticasPublicasResponseDTO update(Integer id, EstadisticasPublicasUpdateDTO dto) {
        EstadisticasPublicasEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estadística no encontrada con ID: " + id));
        
        if (dto.getTitulo() != null) entity.setTitulo(dto.getTitulo());
        if (dto.getValor() != null) entity.setValor(dto.getValor());
        if (dto.getIcono() != null) entity.setIcono(dto.getIcono());
        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());
        if (dto.getOrden() != null) entity.setOrden(dto.getOrden());
        if (dto.getVisible() != null) entity.setVisible(dto.getVisible());
        if (dto.getEstado() != null) entity.setEstado(dto.getEstado());
        
        entity.setFechaActualizacion(LocalDateTime.now());
        
        EstadisticasPublicasEntity updated = repository.save(entity);
        return toResponseDTO(updated);
    }
    
    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Estadística no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }
    
    private EstadisticasPublicasResponseDTO toResponseDTO(EstadisticasPublicasEntity entity) {
        EstadisticasPublicasResponseDTO dto = new EstadisticasPublicasResponseDTO();
        dto.setId(entity.getId());
        dto.setClave(entity.getClave());
        dto.setTitulo(entity.getTitulo());
        dto.setValor(entity.getValor());
        dto.setIcono(entity.getIcono());
        dto.setDescripcion(entity.getDescripcion());
        dto.setOrden(entity.getOrden());
        dto.setVisible(entity.getVisible());
        dto.setEstado(entity.getEstado());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}

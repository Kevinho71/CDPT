package app.perfil.service;

import app.common.exception.DuplicateResourceException;
import app.common.exception.InvalidDataException;
import app.common.exception.ResourceNotFoundException;
import app.perfil.dto.ServicioDTO;
import app.perfil.dto.ServicioResponseDTO;
import app.perfil.entity.ServicioEntity;
import app.perfil.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioServiceImpl implements ServicioService {
    
    @Autowired
    private ServicioRepository servicioRepository;
    
    @Override
    @Transactional
    public ServicioResponseDTO create(ServicioDTO dto) {
        validateData(dto);
        
        if (servicioRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new DuplicateResourceException("Servicio", "nombre", dto.getNombre());
        }
        
        ServicioEntity servicio = new ServicioEntity();
        servicio.setNombre(dto.getNombre());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setCategoria(dto.getCategoria());
        servicio.setOrigen("USUARIO");
        servicio.setEstado(dto.getEstado());
        servicio.setFechaCreacion(LocalDateTime.now());
        
        servicio = servicioRepository.save(servicio);
        return toResponseDTO(servicio);
    }
    
    @Override
    @Transactional
    public ServicioResponseDTO update(Integer id, ServicioDTO dto) {
        ServicioEntity servicio = servicioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Servicio", "id", id));
        
        validateData(dto);
        
        if (!servicio.getNombre().equalsIgnoreCase(dto.getNombre())) {
            if (servicioRepository.existsByNombreIgnoreCase(dto.getNombre())) {
                throw new DuplicateResourceException("Servicio", "nombre", dto.getNombre());
            }
        }
        
        servicio.setNombre(dto.getNombre());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setCategoria(dto.getCategoria());
        servicio.setEstado(dto.getEstado());
        
        servicio = servicioRepository.save(servicio);
        return toResponseDTO(servicio);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ServicioResponseDTO findById(Integer id) {
        ServicioEntity servicio = servicioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Servicio", "id", id));
        return toResponseDTO(servicio);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ServicioResponseDTO> findAll() {
        return servicioRepository.findAll().stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ServicioResponseDTO> findAll(int estado) {
        return servicioRepository.findAll(estado, "").stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public ServicioResponseDTO changeStatus(Integer id) {
        ServicioEntity servicio = servicioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Servicio", "id", id));
        
        servicioRepository.updateStatus(id);
        
        servicio = servicioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Servicio", "id", id));
        
        return toResponseDTO(servicio);
    }
    
    private void validateData(ServicioDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new InvalidDataException("El nombre del servicio es requerido");
        }
        if (dto.getEstado() == null || (dto.getEstado() != 0 && dto.getEstado() != 1)) {
            throw new InvalidDataException("El estado debe ser 0 o 1");
        }
    }
    
    private ServicioResponseDTO toResponseDTO(ServicioEntity entity) {
        return new ServicioResponseDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getDescripcion(),
            entity.getCategoria(),
            entity.getOrigen(),
            entity.getEstado(),
            entity.getFechaCreacion()
        );
    }
}

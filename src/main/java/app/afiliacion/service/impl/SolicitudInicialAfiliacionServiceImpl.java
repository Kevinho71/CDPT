package app.afiliacion.service.impl;

import app.afiliacion.dto.SolicitudInicialAfiliacionCreateDTO;
import app.afiliacion.dto.SolicitudInicialAfiliacionResponseDTO;
import app.afiliacion.dto.SolicitudInicialAfiliacionUpdateDTO;
import app.afiliacion.entity.SolicitudInicialAfiliacionEntity;
import app.afiliacion.repository.SolicitudInicialAfiliacionRepository;
import app.afiliacion.service.SolicitudInicialAfiliacionService;
import app.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SolicitudInicialAfiliacionServiceImpl implements SolicitudInicialAfiliacionService {
    
    @Autowired
    private SolicitudInicialAfiliacionRepository repository;
    
    @Override
    @Transactional(readOnly = true)
    public List<SolicitudInicialAfiliacionResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SolicitudInicialAfiliacionResponseDTO> findByEstado(String estado) {
        return repository.findByEstado(estado).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public SolicitudInicialAfiliacionResponseDTO findById(Integer id) {
        SolicitudInicialAfiliacionEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud inicial no encontrada con ID: " + id));
        return toResponseDTO(entity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public SolicitudInicialAfiliacionResponseDTO findByToken(String token) {
        SolicitudInicialAfiliacionEntity entity = repository.findByTokenAcceso(token)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada con token: " + token));
        return toResponseDTO(entity);
    }
    
    @Override
    public SolicitudInicialAfiliacionResponseDTO create(SolicitudInicialAfiliacionCreateDTO dto) {
        SolicitudInicialAfiliacionEntity entity = new SolicitudInicialAfiliacionEntity();
        entity.setNombreCompleto(dto.getNombreCompleto());
        entity.setCelular(dto.getCelular());
        entity.setCorreo(dto.getCorreo());
        entity.setEstado("PENDIENTE");
        entity.setFechaSolicitud(LocalDateTime.now());
        
        // Generar token de acceso único
        entity.setTokenAcceso(UUID.randomUUID().toString());
        entity.setTokenExpiracion(LocalDateTime.now().plusDays(7));
        
        SolicitudInicialAfiliacionEntity saved = repository.save(entity);
        return toResponseDTO(saved);
    }
    
    @Override
    public SolicitudInicialAfiliacionResponseDTO update(Integer id, SolicitudInicialAfiliacionUpdateDTO dto) {
        SolicitudInicialAfiliacionEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud inicial no encontrada con ID: " + id));
        
        if (dto.getEstado() != null) {
            entity.setEstado(dto.getEstado());
        }
        if (dto.getNotasAdmin() != null) {
            entity.setNotasAdmin(dto.getNotasAdmin());
        }
        
        SolicitudInicialAfiliacionEntity updated = repository.save(entity);
        return toResponseDTO(updated);
    }
    
    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Solicitud inicial no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }
    
    @Override
    public SolicitudInicialAfiliacionResponseDTO generarLinkInscripcion(Integer id) {
        SolicitudInicialAfiliacionEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud inicial no encontrada con ID: " + id));
        
        // Generar token único y configurar expiración
        entity.setTokenAcceso(UUID.randomUUID().toString());
        entity.setTokenExpiracion(LocalDateTime.now().plusDays(7));
        entity.setEstado("LINK_ENVIADO");
        
        SolicitudInicialAfiliacionEntity updated = repository.save(entity);
        return toResponseDTO(updated);
    }
    
    private SolicitudInicialAfiliacionResponseDTO toResponseDTO(SolicitudInicialAfiliacionEntity entity) {
        SolicitudInicialAfiliacionResponseDTO dto = new SolicitudInicialAfiliacionResponseDTO();
        dto.setId(entity.getId());
        dto.setNombreCompleto(entity.getNombreCompleto());
        dto.setCelular(entity.getCelular());
        dto.setCorreo(entity.getCorreo());
        dto.setTokenAcceso(entity.getTokenAcceso());
        dto.setTokenExpiracion(entity.getTokenExpiracion());
        dto.setEstado(entity.getEstado());
        dto.setFechaSolicitud(entity.getFechaSolicitud());
        dto.setNotasAdmin(entity.getNotasAdmin());
        return dto;
    }
}

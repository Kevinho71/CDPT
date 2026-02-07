package app.clinica.service.impl;

import app.clinica.dto.SolicitudCitaCreateDTO;
import app.clinica.dto.SolicitudCitaResponseDTO;
import app.clinica.entity.SolicitudCitaEntity;
import app.clinica.repository.SolicitudCitaRepository;
import app.clinica.service.SolicitudCitaService;
import app.perfil.entity.PerfilSocioEntity;
import app.perfil.repository.PerfilSocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudCitaServiceImpl implements SolicitudCitaService {
    
    @Autowired
    private SolicitudCitaRepository solicitudRepository;
    
    @Autowired
    private PerfilSocioRepository perfilSocioRepository;
    
    @Override
    @Transactional
    public SolicitudCitaResponseDTO registrarSolicitud(Integer perfilSocioId, SolicitudCitaCreateDTO dto) {
        // Validar que el perfil del psicólogo existe
        PerfilSocioEntity perfil = perfilSocioRepository.findById(perfilSocioId)
            .orElseThrow(() -> new RuntimeException("Perfil de psicólogo no encontrado"));
        
        // Crear la solicitud
        SolicitudCitaEntity solicitud = new SolicitudCitaEntity(
            perfil,
            dto.getNombrePaciente(),
            dto.getCelular(),
            dto.getEmail(),
            dto.getMotivoConsulta(),
            dto.getModalidad()
        );
        
        SolicitudCitaEntity guardada = solicitudRepository.save(solicitud);
        
        return mapearADTO(guardada);
    }
    
    @Override
    public List<SolicitudCitaResponseDTO> obtenerSolicitudesPorPsicologo(Integer perfilSocioId) {
        List<SolicitudCitaEntity> solicitudes = 
            solicitudRepository.findByPerfilSocio_IdOrderByFechaSolicitudDesc(perfilSocioId);
        
        return solicitudes.stream()
            .map(this::mapearADTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<SolicitudCitaResponseDTO> obtenerSolicitudesPorEstado(Integer perfilSocioId, String estado) {
        List<SolicitudCitaEntity> solicitudes = 
            solicitudRepository.findByPerfilSocio_IdAndEstadoOrderByFechaSolicitudDesc(perfilSocioId, estado);
        
        return solicitudes.stream()
            .map(this::mapearADTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public SolicitudCitaResponseDTO marcarComoContactado(Integer solicitudId) {
        SolicitudCitaEntity solicitud = solicitudRepository.findById(solicitudId)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        
        solicitud.setEstado("CONTACTADO");
        solicitud.setFechaActualizacion(LocalDateTime.now());
        
        SolicitudCitaEntity actualizada = solicitudRepository.save(solicitud);
        return mapearADTO(actualizada);
    }
    
    @Override
    @Transactional
    public SolicitudCitaResponseDTO marcarComoConvertido(Integer solicitudId) {
        SolicitudCitaEntity solicitud = solicitudRepository.findById(solicitudId)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        
        solicitud.setEstado("CONVERTIDO");
        solicitud.setFechaActualizacion(LocalDateTime.now());
        
        SolicitudCitaEntity actualizada = solicitudRepository.save(solicitud);
        return mapearADTO(actualizada);
    }
    
    @Override
    @Transactional
    public SolicitudCitaResponseDTO marcarComoDescartado(Integer solicitudId, String motivo) {
        SolicitudCitaEntity solicitud = solicitudRepository.findById(solicitudId)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        
        solicitud.setEstado("DESCARTADO");
        solicitud.setNotaInterna(motivo != null ? motivo : "Descartado");
        solicitud.setFechaActualizacion(LocalDateTime.now());
        
        SolicitudCitaEntity actualizada = solicitudRepository.save(solicitud);
        return mapearADTO(actualizada);
    }
    
    @Override
    public long contarSolicitudesPendientes(Integer perfilSocioId) {
        return solicitudRepository.countByPerfilSocio_IdAndEstado(perfilSocioId, "PENDIENTE");
    }
    
    @Override
    @Transactional
    public SolicitudCitaResponseDTO actualizarNotaInterna(Integer solicitudId, String nota) {
        SolicitudCitaEntity solicitud = solicitudRepository.findById(solicitudId)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        
        solicitud.setNotaInterna(nota);
        solicitud.setFechaActualizacion(LocalDateTime.now());
        
        SolicitudCitaEntity actualizada = solicitudRepository.save(solicitud);
        return mapearADTO(actualizada);
    }
    
    // Método auxiliar para mapear entidad a DTO
    private SolicitudCitaResponseDTO mapearADTO(SolicitudCitaEntity solicitud) {
        return new SolicitudCitaResponseDTO(
            solicitud.getId(),
            solicitud.getPerfilSocio().getId(),
            solicitud.getPerfilSocio().getNombreCompleto(),
            solicitud.getNombrePaciente(),
            solicitud.getCelular(),
            solicitud.getEmail(),
            solicitud.getMotivoConsulta(),
            solicitud.getModalidad(),
            solicitud.getEstado(),
            solicitud.getNotaInterna(),
            solicitud.getFechaSolicitud(),
            solicitud.getFechaActualizacion()
        );
    }
}

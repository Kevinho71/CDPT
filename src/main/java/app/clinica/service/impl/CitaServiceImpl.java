package app.clinica.service.impl;

import app.clinica.dto.CitaCreateDTO;
import app.clinica.dto.CitaResponseDTO;
import app.clinica.dto.CitaUpdateDTO;
import app.clinica.entity.CitaEntity;
import app.clinica.entity.PacienteEntity;
import app.clinica.repository.CitaRepository;
import app.clinica.repository.PacienteRepository;
import app.clinica.service.CitaService;
import app.common.exception.ResourceNotFoundException;
import app.perfil.entity.PerfilSocioEntity;
import app.perfil.repository.PerfilSocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CitaServiceImpl implements CitaService {
    
    @Autowired
    private CitaRepository repository;
    
    @Autowired
    private PerfilSocioRepository perfilSocioRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<CitaResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CitaResponseDTO> findByPerfilSocio(Integer perfilSocioId) {
        return repository.findByFkPerfilSocio_Id(perfilSocioId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CitaResponseDTO> findByPaciente(Integer pacienteId) {
        return repository.findByFkPaciente_Id(pacienteId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CitaResponseDTO> findByFecha(LocalDate fechaCita) {
        return repository.findByFechaCita(fechaCita).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CitaResponseDTO> findByEstado(String estadoCita) {
        return repository.findByEstadoCita(estadoCita).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public CitaResponseDTO findById(Integer id) {
        CitaEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
        return toResponseDTO(entity);
    }
    
    @Override
    public CitaResponseDTO create(CitaCreateDTO dto) {
        CitaEntity entity = new CitaEntity();
        
        PerfilSocioEntity perfilSocio = perfilSocioRepository.findById(dto.getFkPerfilSocio())
                .orElseThrow(() -> new ResourceNotFoundException("Perfil socio no encontrado con ID: " + dto.getFkPerfilSocio()));
        entity.setPerfilSocio(perfilSocio);
        
        PacienteEntity paciente = pacienteRepository.findById(dto.getFkPaciente())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + dto.getFkPaciente()));
        entity.setPaciente(paciente);
        
        entity.setFechaCita(dto.getFechaCita());
        entity.setHoraInicio(dto.getHoraInicio());
        entity.setHoraFin(dto.getHoraFin());
        entity.setModalidad(dto.getModalidad());
        entity.setTipoSesion(dto.getTipoSesion());
        entity.setMotivoBreve(dto.getMotivoBreve());
        entity.setNotasInternas(dto.getNotasInternas());
        entity.setMontoAcordado(dto.getMontoAcordado() != null ? dto.getMontoAcordado() : java.math.BigDecimal.ZERO);
        entity.setEstadoCita("PROGRAMADA"); // Estado por defecto
        
        CitaEntity saved = repository.save(entity);
        return toResponseDTO(saved);
    }
    
    @Override
    public CitaResponseDTO update(Integer id, CitaUpdateDTO dto) {
        CitaEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
        
        if (dto.getFechaCita() != null) entity.setFechaCita(dto.getFechaCita());
        if (dto.getHoraInicio() != null) entity.setHoraInicio(dto.getHoraInicio());
        if (dto.getHoraFin() != null) entity.setHoraFin(dto.getHoraFin());
        if (dto.getModalidad() != null) entity.setModalidad(dto.getModalidad());
        if (dto.getTipoSesion() != null) entity.setTipoSesion(dto.getTipoSesion());
        if (dto.getMotivoBreve() != null) entity.setMotivoBreve(dto.getMotivoBreve());
        if (dto.getNotasInternas() != null) entity.setNotasInternas(dto.getNotasInternas());
        if (dto.getEstadoCita() != null) entity.setEstadoCita(dto.getEstadoCita());
        
        CitaEntity updated = repository.save(entity);
        return toResponseDTO(updated);
    }
    
    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cita no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }
    
    /**
     * ADVERTENCIA: Este servicio usa el modelo clásico "1 Cita = 1 Paciente".
     * Para terapias multi-paciente (pareja, familiar), usar CitaSistemicaService.
     */
    private CitaResponseDTO toResponseDTO(CitaEntity entity) {
        CitaResponseDTO dto = new CitaResponseDTO();
        dto.setId(entity.getId());
        dto.setFkPerfilSocio(entity.getPerfilSocio() != null ? entity.getPerfilSocio().getId() : null);
        dto.setPerfilSocioNombre(entity.getPerfilSocio() != null && entity.getPerfilSocio().getSocio() != null 
                ? entity.getPerfilSocio().getSocio().getNombresocio() : null);
        dto.setFkPaciente(entity.getPaciente() != null ? entity.getPaciente().getId() : null);
        dto.setPacienteNombre(entity.getPaciente() != null 
                ? entity.getPaciente().getNombres() + " " + entity.getPaciente().getApellidos() : null);
        dto.setFechaCita(entity.getFechaCita());
        dto.setHoraInicio(entity.getHoraInicio());
        dto.setHoraFin(entity.getHoraFin());
        dto.setModalidad(entity.getModalidad());
        dto.setTipoSesion(entity.getTipoSesion());
        dto.setMotivoBreve(entity.getMotivoBreve()); // CORREGIDO: era setMotivo
        dto.setNotasInternas(entity.getNotasInternas()); // CORREGIDO: era setObservaciones
        dto.setMontoAcordado(entity.getMontoAcordado());
        dto.setEstadoCita(entity.getEstadoCita());
        dto.setFechaCreacion(entity.getFechaCreacion());
        
        // Nota: Este DTO no incluye participantes (modelo clásico)
        // Para obtener participantes, usar CitaSistemicaService
        
        return dto;
    }
}

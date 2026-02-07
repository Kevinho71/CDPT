package app.clinica.service.impl;

import app.clinica.dto.PacienteCreateDTO;
import app.clinica.dto.PacienteResponseDTO;
import app.clinica.dto.PacienteUpdateDTO;
import app.clinica.entity.PacienteEntity;
import app.clinica.repository.PacienteRepository;
import app.clinica.service.PacienteService;
import app.common.exception.ResourceNotFoundException;
import app.socio.entity.SocioEntity;
import app.socio.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {
    
    @Autowired
    private PacienteRepository repository;
    
    @Autowired
    private SocioRepository socioRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> findBySocio(Integer socioId) {
        return repository.findByFkSocio_Id(socioId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> findByEstado(Integer estado) {
        return repository.findByEstado(estado).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDTO findById(Integer id) {
        PacienteEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
        return toResponseDTO(entity);
    }
    
    @Override
    public PacienteResponseDTO create(PacienteCreateDTO dto) {
        PacienteEntity entity = new PacienteEntity();
        
        SocioEntity socio = socioRepository.findById(dto.getFkSocio())
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado con ID: " + dto.getFkSocio()));
        entity.setSocio(socio);
        
        entity.setNombres(dto.getNombres());
        entity.setApellidos(dto.getApellidos());
        entity.setCi(dto.getCi());
        entity.setFechaNacimiento(dto.getFechaNacimiento());
        entity.setGenero(dto.getGenero());
        entity.setTelefono(dto.getTelefono());
        entity.setEmail(dto.getEmail());
        entity.setDireccion(dto.getDireccion());
        entity.setOcupacion(dto.getOcupacion());
        entity.setEstadoCivil(dto.getEstadoCivil());
        entity.setNombreContactoEmergencia(dto.getNombreContactoEmergencia());
        entity.setTelefonoContactoEmergencia(dto.getTelefonoContactoEmergencia());
        entity.setEstado(1); // Activo por defecto
        
        PacienteEntity saved = repository.save(entity);
        return toResponseDTO(saved);
    }
    
    @Override
    public PacienteResponseDTO update(Integer id, PacienteUpdateDTO dto) {
        PacienteEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
        
        if (dto.getNombres() != null) entity.setNombres(dto.getNombres());
        if (dto.getApellidos() != null) entity.setApellidos(dto.getApellidos());
        if (dto.getCi() != null) entity.setCi(dto.getCi());
        if (dto.getFechaNacimiento() != null) entity.setFechaNacimiento(dto.getFechaNacimiento());
        if (dto.getGenero() != null) entity.setGenero(dto.getGenero());
        if (dto.getTelefono() != null) entity.setTelefono(dto.getTelefono());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getDireccion() != null) entity.setDireccion(dto.getDireccion());
        if (dto.getOcupacion() != null) entity.setOcupacion(dto.getOcupacion());
        if (dto.getEstadoCivil() != null) entity.setEstadoCivil(dto.getEstadoCivil());
        if (dto.getNombreContactoEmergencia() != null) entity.setNombreContactoEmergencia(dto.getNombreContactoEmergencia());
        if (dto.getTelefonoContactoEmergencia() != null) entity.setTelefonoContactoEmergencia(dto.getTelefonoContactoEmergencia());
        if (dto.getEstado() != null) entity.setEstado(dto.getEstado());
        
        PacienteEntity updated = repository.save(entity);
        return toResponseDTO(updated);
    }
    
    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }
    
    /**
     * Mapea entidad Paciente a DTO de respuesta.
     * NOTA: Un paciente puede participar en múltiples citas (modelo sistémico).
     */
    private PacienteResponseDTO toResponseDTO(PacienteEntity entity) {
        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.setId(entity.getId());
        dto.setFkSocio(entity.getSocio() != null ? entity.getSocio().getId() : null);
        dto.setSocioNombre(entity.getSocio() != null ? entity.getSocio().getNombresocio() : null);
        dto.setNombres(entity.getNombres());
        dto.setApellidos(entity.getApellidos());
        dto.setCi(entity.getCi());
        dto.setFechaNacimiento(entity.getFechaNacimiento());
        dto.setGenero(entity.getGenero());
        dto.setTelefono(entity.getTelefono());
        dto.setEmail(entity.getEmail());
        dto.setDireccion(entity.getDireccion());
        dto.setOcupacion(entity.getOcupacion());
        dto.setEstadoCivil(entity.getEstadoCivil());
        dto.setNombreContactoEmergencia(entity.getNombreContactoEmergencia());
        dto.setTelefonoContactoEmergencia(entity.getTelefonoContactoEmergencia());
        dto.setFechaRegistro(entity.getFechaRegistro());
        dto.setEstado(entity.getEstado());
        return dto;
    }
}

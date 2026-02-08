package app.clinica.service.impl;

import app.clinica.dto.HistoriaClinicaCreateDTO;
import app.clinica.dto.HistoriaClinicaResponseDTO;
import app.clinica.dto.HistoriaClinicaUpdateDTO;
import app.clinica.entity.CitaEntity;
import app.clinica.entity.HistoriaClinicaEntity;
import app.clinica.entity.PacienteEntity;
import app.clinica.repository.CitaRepository;
import app.clinica.repository.HistoriaClinicaRepository;
import app.clinica.repository.PacienteRepository;
import app.clinica.service.HistoriaClinicaService;
import app.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HistoriaClinicaServiceImpl implements HistoriaClinicaService {
    
    @Autowired
    private HistoriaClinicaRepository repository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<HistoriaClinicaResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<HistoriaClinicaResponseDTO> findByPaciente(Integer pacienteId) {
        return repository.findByPaciente_Id(pacienteId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * IMPLEMENTACIÓN DEL MÉTODO SISTÉMICO CRÍTICO.
     * 
     * Utiliza la query JPQL especializada que hace JOIN con cita_participantes.
     * Esta query trae:
     * 1. Historias donde fk_paciente = pacienteId (notas directas)
     * 2. Historias donde el paciente está en cita_participantes (notas compartidas)
     * 
     * Resultado: Historial completo cronológico del paciente.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HistoriaClinicaResponseDTO> findHistorialSistemicoPorPaciente(Integer pacienteId) {
        return repository.findHistorialSistemicoPorPaciente(pacienteId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public HistoriaClinicaResponseDTO findById(Integer id) {
        HistoriaClinicaEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historia clínica no encontrada con ID: " + id));
        return toResponseDTO(entity);
    }
    
    @Override
    public HistoriaClinicaResponseDTO create(HistoriaClinicaCreateDTO dto) {
        HistoriaClinicaEntity entity = new HistoriaClinicaEntity();
        
        // MODELO SISTÉMICO: fkPaciente puede ser NULL
        // - Si es NULL, la nota es de una sesión grupal y se vincula solo a la cita
        // - Si tiene valor, es una nota individual vinculada al paciente
        if (dto.getFkPaciente() != null) {
            PacienteEntity paciente = pacienteRepository.findById(dto.getFkPaciente())
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + dto.getFkPaciente()));
            entity.setPaciente(paciente);
        }
        
        if (dto.getFkCita() != null) {
            CitaEntity cita = citaRepository.findById(dto.getFkCita())
                    .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + dto.getFkCita()));
            entity.setCita(cita);
        }
        
        // Validación: debe tener al menos paciente o cita
        if (dto.getFkPaciente() == null && dto.getFkCita() == null) {
            throw new IllegalArgumentException(
                "La historia clínica debe estar vinculada a un paciente, a una cita, o a ambos");
        }
        
        entity.setFechaConsulta(dto.getFechaConsulta());
        entity.setMotivoConsulta(dto.getMotivoConsulta());
        entity.setObservaciones(dto.getObservaciones());
        entity.setDiagnostico(dto.getDiagnostico());
        entity.setTratamientoPlan(dto.getTratamientoPlan());
        entity.setEvolucion(dto.getEvolucion());
        entity.setArchivosAdjuntos(dto.getArchivosAdjuntos());
        
        HistoriaClinicaEntity saved = repository.save(entity);
        return toResponseDTO(saved);
    }
    
    @Override
    public HistoriaClinicaResponseDTO update(Integer id, HistoriaClinicaUpdateDTO dto) {
        HistoriaClinicaEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historia clínica no encontrada con ID: " + id));
        
        if (dto.getFechaConsulta() != null) entity.setFechaConsulta(dto.getFechaConsulta());
        if (dto.getMotivoConsulta() != null) entity.setMotivoConsulta(dto.getMotivoConsulta());
        if (dto.getObservaciones() != null) entity.setObservaciones(dto.getObservaciones());
        if (dto.getDiagnostico() != null) entity.setDiagnostico(dto.getDiagnostico());
        if (dto.getTratamientoPlan() != null) entity.setTratamientoPlan(dto.getTratamientoPlan());
        if (dto.getEvolucion() != null) entity.setEvolucion(dto.getEvolucion());
        if (dto.getArchivosAdjuntos() != null) entity.setArchivosAdjuntos(dto.getArchivosAdjuntos());
        
        entity.setFechaActualizacion(java.time.LocalDateTime.now());
        
        HistoriaClinicaEntity updated = repository.save(entity);
        return toResponseDTO(updated);
    }
    
    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Historia clínica no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }
    
    private HistoriaClinicaResponseDTO toResponseDTO(HistoriaClinicaEntity entity) {
        HistoriaClinicaResponseDTO dto = new HistoriaClinicaResponseDTO();
        dto.setId(entity.getId());
        dto.setFkPaciente(entity.getPaciente() != null ? entity.getPaciente().getId() : null);
        dto.setPacienteNombre(entity.getPaciente() != null 
                ? entity.getPaciente().getNombres() + " " + entity.getPaciente().getApellidos() : null);
        dto.setFkCita(entity.getCita() != null ? entity.getCita().getId() : null);
        dto.setFechaConsulta(entity.getFechaConsulta());
        dto.setMotivoConsulta(entity.getMotivoConsulta());
        dto.setObservaciones(entity.getObservaciones());
        dto.setDiagnostico(entity.getDiagnostico());
        dto.setTratamientoPlan(entity.getTratamientoPlan());
        dto.setEvolucion(entity.getEvolucion());
        dto.setArchivosAdjuntos(entity.getArchivosAdjuntos());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}

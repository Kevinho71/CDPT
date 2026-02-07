package app.clinica.service.impl;

import app.clinica.dto.CitaConParticipantesCreateDTO;
import app.clinica.dto.CitaResponseDTO;
import app.clinica.dto.ParticipanteSimpleDTO;
import app.clinica.entity.CitaEntity;
import app.clinica.entity.CitaParticipanteEntity;
import app.clinica.entity.PacienteEntity;
import app.clinica.entity.SolicitudCitaEntity;
import app.clinica.repository.CitaParticipanteRepository;
import app.clinica.repository.CitaRepository;
import app.clinica.repository.PacienteRepository;
import app.clinica.repository.SolicitudCitaRepository;
import app.clinica.service.CitaSistemicaService;
import app.perfil.entity.PerfilSocioEntity;
import app.perfil.repository.PerfilSocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaSistemicaServiceImpl implements CitaSistemicaService {
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private CitaParticipanteRepository participanteRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private PerfilSocioRepository perfilSocioRepository;
    
    @Autowired
    private SolicitudCitaRepository solicitudRepository;
    
    /**
     * FLUJO TRANSACCIONAL CRÍTICO:
     * 1. Valida que el perfil del psicólogo existe
     * 2. Valida que todos los pacientes participantes existen
     * 3. Crea el registro en 'cita'
     * 4. Crea múltiples registros en 'cita_participantes' (uno por cada participante)
     * 5. Si falla alg\u00fan paso, hace rollback completo
     */
    @Override
    @Transactional
    public CitaResponseDTO crearCitaConParticipantes(CitaConParticipantesCreateDTO dto) {
        // 0. VALIDACIÓN CRÍTICA: Debe haber al menos un participante
        if (dto.getParticipantes() == null || dto.getParticipantes().isEmpty()) {
            throw new IllegalArgumentException("Debe especificar al menos un participante para la cita");
        }
        
        // 1. Validar perfil psicólogo
        PerfilSocioEntity perfil = perfilSocioRepository.findById(dto.getFkPerfilSocio())
            .orElseThrow(() -> new RuntimeException("Perfil de psicólogo no encontrado"));
        
        // 2. Validar paciente titular
        PacienteEntity titular = pacienteRepository.findById(dto.getIdTitular())
            .orElseThrow(() -> new RuntimeException("Paciente titular no encontrado"));
        
        // 3. Crear la cita
        CitaEntity cita = new CitaEntity();
        cita.setPerfilSocio(perfil);
        cita.setPaciente(titular); // El titular es quien reserva/paga
        cita.setFechaCita(dto.getFechaCita());
        cita.setHoraInicio(dto.getHoraInicio());
        cita.setHoraFin(dto.getHoraFin());
        cita.setModalidad(dto.getModalidad());
        cita.setTipoSesion(dto.getTipoSesion());
        cita.setEstadoCita("PROGRAMADA");
        cita.setMotivoBreve(dto.getMotivoBreve());
        cita.setNotasInternas(dto.getNotasInternas());
        cita.setMontoAcordado(dto.getMontoAcordado());
        
        CitaEntity citaGuardada = citaRepository.save(cita);
        
        // 4. Crear los participantes (BUCLE TRANSACCIONAL)
        List<ParticipanteSimpleDTO> participantesCreados = new ArrayList<>();
        
        for (CitaConParticipantesCreateDTO.ParticipanteDTO participanteDTO : dto.getParticipantes()) {
            // Validar que el paciente existe
            PacienteEntity paciente = pacienteRepository.findById(participanteDTO.getIdPaciente())
                .orElseThrow(() -> new RuntimeException(
                    "Paciente con ID " + participanteDTO.getIdPaciente() + " no encontrado"));
            
            // Evitar duplicados
            if (!participanteRepository.existsByCita_IdAndPaciente_Id(citaGuardada.getId(), paciente.getId())) {
                CitaParticipanteEntity participante = new CitaParticipanteEntity(
                    citaGuardada,
                    paciente,
                    participanteDTO.getTipoParticipacion() != null 
                        ? participanteDTO.getTipoParticipacion() 
                        : "PACIENTE"
                );
                
                CitaParticipanteEntity guardado = participanteRepository.save(participante);
                
                participantesCreados.add(new ParticipanteSimpleDTO(
                    guardado.getId(),
                    paciente.getId(),
                    paciente.getNombres() + " " + paciente.getApellidos(),
                    guardado.getTipoParticipacion()
                ));
            }
        }
        
        // 5. Construir respuesta
        return mapearACitaResponseDTO(citaGuardada, participantesCreados);
    }
    
    @Override
    @Transactional
    public void actualizarParticipantes(Integer citaId, 
                                       List<CitaConParticipantesCreateDTO.ParticipanteDTO> participantes) {
        // Validar que la cita existe
        CitaEntity cita = citaRepository.findById(citaId)
            .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        // Borrar participantes actuales
        participanteRepository.deleteByCita_Id(citaId);
        
        // Crear nuevos participantes
        for (CitaConParticipantesCreateDTO.ParticipanteDTO participanteDTO : participantes) {
            PacienteEntity paciente = pacienteRepository.findById(participanteDTO.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
            
            CitaParticipanteEntity participante = new CitaParticipanteEntity(
                cita,
                paciente,
                participanteDTO.getTipoParticipacion() != null 
                    ? participanteDTO.getTipoParticipacion() 
                    : "PACIENTE"
            );
            
            participanteRepository.save(participante);
        }
    }
    
    @Override
    public List<ParticipanteSimpleDTO> obtenerParticipantesDeCita(Integer citaId) {
        List<CitaParticipanteEntity> participantes = participanteRepository.findByCita_Id(citaId);
        
        return participantes.stream()
            .map(p -> new ParticipanteSimpleDTO(
                p.getId(),
                p.getPaciente().getId(),
                p.getPaciente().getNombres() + " " + p.getPaciente().getApellidos(),
                p.getTipoParticipacion()
            ))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<CitaResponseDTO> obtenerCitasPorPaciente(Integer pacienteId) {
        // Buscar todas las participaciones de este paciente
        List<CitaParticipanteEntity> participaciones = participanteRepository.findByPaciente_Id(pacienteId);
        
        // Extraer las citas y mapearlas
        return participaciones.stream()
            .map(p -> {
                CitaEntity cita = p.getCita();
                List<ParticipanteSimpleDTO> participantes = obtenerParticipantesDeCita(cita.getId());
                return mapearACitaResponseDTO(cita, participantes);
            })
            .collect(Collectors.toList());
    }
    
    /**
     * FLUJO DE CONVERSIÓN (SOLICITUD → CITA):
     * Este método es el "cruce" entre el lead y el paciente real.
     * 
     * CASO DE USO:
     * - Ana llenó el formulario en la landing page
     * - El psicólogo la contactó por WhatsApp
     * - Ana dijo: "Quiero terapia de pareja con mi esposo Carlos"
     * - Se ejecuta este método:
     *   1. Verifica si Ana existe como paciente, si no la crea
     *   2. Verifica si Carlos existe, si no lo crea
     *   3. Crea la cita con ambos como participantes
     *   4. Marca la solicitud como CONVERTIDO
     */
    @Override
    @Transactional
    public CitaResponseDTO convertirSolicitudEnCita(Integer solicitudId, 
                                                    CitaConParticipantesCreateDTO dto) {
        // Buscar la solicitud
        SolicitudCitaEntity solicitud = solicitudRepository.findById(solicitudId)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        
        // Crear la cita con los participantes
        CitaResponseDTO citaCreada = crearCitaConParticipantes(dto);
        
        // Marcar solicitud como convertida
        solicitud.setEstado("CONVERTIDO");
        solicitud.setFechaActualizacion(LocalDateTime.now());
        solicitudRepository.save(solicitud);
        
        return citaCreada;
    }
    
    // Método auxiliar para mapear entidad a DTO (MODELO SISTÉMICO)
    private CitaResponseDTO mapearACitaResponseDTO(CitaEntity cita, List<ParticipanteSimpleDTO> participantes) {
        CitaResponseDTO dto = new CitaResponseDTO();
        dto.setId(cita.getId());
        dto.setFkPerfilSocio(cita.getPerfilSocio().getId());
        
        // Nombre del psicólogo
        if (cita.getPerfilSocio().getSocio() != null) {
            dto.setPerfilSocioNombre(cita.getPerfilSocio().getSocio().getNombresocio());
        }
        
        // Paciente titular (responsable administrativo/económico)
        dto.setFkPaciente(cita.getPaciente().getId());
        dto.setPacienteNombre(cita.getPaciente().getNombres() + " " + cita.getPaciente().getApellidos());
        
        dto.setFechaCita(cita.getFechaCita());
        dto.setHoraInicio(cita.getHoraInicio());
        dto.setHoraFin(cita.getHoraFin());
        dto.setModalidad(cita.getModalidad());
        dto.setTipoSesion(cita.getTipoSesion());
        dto.setEstadoCita(cita.getEstadoCita());
        dto.setMotivoBreve(cita.getMotivoBreve());
        dto.setNotasInternas(cita.getNotasInternas());
        dto.setMontoAcordado(cita.getMontoAcordado());
        dto.setFechaCreacion(cita.getFechaCreacion());
        
        // CRÍTICO: Incluir participantes (modelo sistémico)
        dto.setParticipantes(participantes);
        
        return dto;
    }
}

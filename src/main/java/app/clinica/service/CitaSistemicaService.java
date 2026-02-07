package app.clinica.service;

import app.clinica.dto.CitaConParticipantesCreateDTO;
import app.clinica.dto.CitaResponseDTO;
import app.clinica.dto.ParticipanteSimpleDTO;
import java.util.List;

/**
 * Servicio para gestión de citas con modelo sistémico (múltiples participantes).
 */
public interface CitaSistemicaService {
    
    /**
     * Crea una cita con múltiples participantes (pareja, familia, grupo).
     * TRANSACCIONAL: Inserta en 'cita' y múltiples filas en 'cita_participantes'.
     * 
     * @param dto Datos de la cita con lista de participantes
     * @return DTO de respuesta con la cita creada
     */
    CitaResponseDTO crearCitaConParticipantes(CitaConParticipantesCreateDTO dto);
    
    /**
     * Actualiza los participantes de una cita existente.
     * TRANSACCIONAL: Borra los participantes actuales y crea los nuevos.
     * 
     * @param citaId ID de la cita
     * @param participantes Nuevos participantes
     */
    void actualizarParticipantes(Integer citaId, List<CitaConParticipantesCreateDTO.ParticipanteDTO> participantes);
    
    /**
     * Obtiene la lista de participantes de una cita.
     * 
     * @param citaId ID de la cita
     * @return Lista de participantes con sus datos básicos
     */
    List<ParticipanteSimpleDTO> obtenerParticipantesDeCita(Integer citaId);
    
    /**
     * Obtiene todas las citas en las que un paciente participó.
     * Útil para ver "El historial de citas de Carlos".
     * 
     * @param pacienteId ID del paciente
     * @return Lista de citas donde participó
     */
    List<CitaResponseDTO> obtenerCitasPorPaciente(Integer pacienteId);
    
    /**
     * Convierte una solicitud de cita en una cita real.
     * TRANSACCIONAL: Crea pacientes si no existen, crea cita, marca solicitud como CONVERTIDO.
     * 
     * @param solicitudId ID de la solicitud
     * @param dto Datos adicionales para la cita
     * @return La cita creada
     */
    CitaResponseDTO convertirSolicitudEnCita(Integer solicitudId, CitaConParticipantesCreateDTO dto);
}

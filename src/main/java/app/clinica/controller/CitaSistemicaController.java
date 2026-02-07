package app.clinica.controller;

import app.clinica.dto.CitaConParticipantesCreateDTO;
import app.clinica.dto.CitaResponseDTO;
import app.clinica.dto.ParticipanteSimpleDTO;
import app.clinica.service.CitaSistemicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de citas con modelo sistémico (múltiples participantes).
 * Soporta terapias individuales, de pareja, familiares y grupales.
 */
@RestController
@RequestMapping("/api/clinica/citas-sistemicas")
@CrossOrigin(origins = "*")
public class CitaSistemicaController {
    
    @Autowired
    private CitaSistemicaService citaSistemicaService;
    
    /**
     * POST /api/clinica/citas-sistemicas
     * 
     * Crea una cita con múltiples participantes.
     * 
     * EJEMPLO DE PAYLOAD JSON:
     * {
     *   "fkPerfilSocio": 5,
     *   "idTitular": 105,
     *   "participantes": [
     *     {"idPaciente": 105, "tipoParticipacion": "TITULAR"},
     *     {"idPaciente": 106, "tipoParticipacion": "PAREJA"}
     *   ],
     *   "fechaCita": "2026-02-20",
     *   "horaInicio": "16:00",
     *   "horaFin": "17:00",
     *   "tipoSesion": "PAREJA",
     *   "modalidad": "PRESENCIAL",
     *   "motivoBreve": "Terapia de pareja - Problemas de comunicación",
     *   "montoAcordado": 200.00
     * }
     */
    @PostMapping
    public ResponseEntity<CitaResponseDTO> crearCitaConParticipantes(
            @Valid @RequestBody CitaConParticipantesCreateDTO dto) {
        CitaResponseDTO nuevaCita = citaSistemicaService.crearCitaConParticipantes(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
    }
    
    /**
     * GET /api/clinica/citas-sistemicas/{citaId}/participantes
     * 
     * Obtiene la lista de participantes de una cita.
     * Útil para ver "Quiénes asistieron a la sesión del 20 de febrero".
     */
    @GetMapping("/{citaId}/participantes")
    public ResponseEntity<List<ParticipanteSimpleDTO>> obtenerParticipantes(
            @PathVariable Integer citaId) {
        List<ParticipanteSimpleDTO> participantes = citaSistemicaService.obtenerParticipantesDeCita(citaId);
        return ResponseEntity.ok(participantes);
    }
    
    /**
     * PUT /api/clinica/citas-sistemicas/{citaId}/participantes
     * 
     * Actualiza los participantes de una cita existente.
     * CUIDADO: Esto borra los participantes actuales y crea los nuevos.
     * 
     * EJEMPLO: Inicialmente era solo Ana, luego agregamos a Carlos.
     * {
     *   "participantes": [
     *     {"idPaciente": 105, "tipoParticipacion": "TITULAR"},
     *     {"idPaciente": 106, "tipoParticipacion": "PAREJA"}
     *   ]
     * }
     */
    @PutMapping("/{citaId}/participantes")
    public ResponseEntity<Void> actualizarParticipantes(
            @PathVariable Integer citaId,
            @RequestBody List<CitaConParticipantesCreateDTO.ParticipanteDTO> participantes) {
        citaSistemicaService.actualizarParticipantes(citaId, participantes);
        return ResponseEntity.ok().build();
    }
    
    /**
     * GET /api/clinica/citas-sistemicas/paciente/{pacienteId}
     * 
     * Obtiene todas las citas en las que un paciente participó.
     * Incluye tanto sesiones individuales como grupales.
     * 
     * Útil para mostrar "El historial de citas de Carlos".
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaResponseDTO>> obtenerCitasPorPaciente(
            @PathVariable Integer pacienteId) {
        List<CitaResponseDTO> citas = citaSistemicaService.obtenerCitasPorPaciente(pacienteId);
        return ResponseEntity.ok(citas);
    }
    
    /**
     * POST /api/clinica/citas-sistemicas/convertir-solicitud/{solicitudId}
     * 
     * Convierte una solicitud de cita (lead) en una cita real.
     * 
     * FLUJO: 
     * 1. El psicólogo contactó al lead por WhatsApp
     * 2. Acordaron fecha/hora
     * 3. Este endpoint crea la cita y marca solicitud como CONVERTIDO
     * 
     * NOTA: Este endpoint puede crear pacientes si no existen (usa sus nombres del DTO).
     */
    @PostMapping("/convertir-solicitud/{solicitudId}")
    public ResponseEntity<CitaResponseDTO> convertirSolicitudEnCita(
            @PathVariable Integer solicitudId,
            @Valid @RequestBody CitaConParticipantesCreateDTO dto) {
        CitaResponseDTO cita = citaSistemicaService.convertirSolicitudEnCita(solicitudId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cita);
    }
}

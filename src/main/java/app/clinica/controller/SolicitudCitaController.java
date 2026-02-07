package app.clinica.controller;

import app.clinica.dto.SolicitudCitaCreateDTO;
import app.clinica.dto.SolicitudCitaResponseDTO;
import app.clinica.service.SolicitudCitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestión de solicitudes de cita (leads desde landing page).
 * Este es el "cruce" entre el lead y el sistema clínico.
 */
@RestController
@RequestMapping("/api/clinica/solicitudes-cita")
@CrossOrigin(origins = "*")
public class SolicitudCitaController {
    
    @Autowired
    private SolicitudCitaService solicitudService;
    
    /**
     * POST /api/public/solicitudes-cita/{perfilSocioId}
     * 
     * ENDPOINT PÚBLICO: Recibe solicitudes desde la landing page.
     * El visitante llena el formulario de contacto.
     * 
     * EJEMPLO DE PAYLOAD:
     * {
     *   "nombrePaciente": "Ana Pérez",
     *   "celular": "77123456",
     *   "email": "ana@example.com",
     *   "motivoConsulta": "Busco terapia de pareja",
     *   "modalidad": "PRESENCIAL"
     * }
     */
    @PostMapping("/public/{perfilSocioId}")
    public ResponseEntity<SolicitudCitaResponseDTO> registrarSolicitud(
            @PathVariable Integer perfilSocioId,
            @Valid @RequestBody SolicitudCitaCreateDTO dto) {
        SolicitudCitaResponseDTO solicitud = solicitudService.registrarSolicitud(perfilSocioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitud);
    }
    
    /**
     * GET /api/clinica/solicitudes-cita/psicologo/{perfilSocioId}
     * 
     * Obtiene todas las solicitudes recibidas por un psicólogo.
     * Ordenadas por fecha (más recientes primero).
     * 
     * Útil para mostrar la "Bandeja de Entrada" del psicólogo.
     */
    @GetMapping("/psicologo/{perfilSocioId}")
    public ResponseEntity<List<SolicitudCitaResponseDTO>> obtenerSolicitudesPorPsicologo(
            @PathVariable Integer perfilSocioId) {
        List<SolicitudCitaResponseDTO> solicitudes = 
            solicitudService.obtenerSolicitudesPorPsicologo(perfilSocioId);
        return ResponseEntity.ok(solicitudes);
    }
    
    /**
     * GET /api/clinica/solicitudes-cita/psicologo/{perfilSocioId}/estado/{estado}
     * 
     * Filtra solicitudes por estado.
     * Estados: PENDIENTE, CONTACTADO, CONVERTIDO, DESCARTADO
     * 
     * Útil para mostrar "Solo las pendientes que no he visto".
     */
    @GetMapping("/psicologo/{perfilSocioId}/estado/{estado}")
    public ResponseEntity<List<SolicitudCitaResponseDTO>> obtenerSolicitudesPorEstado(
            @PathVariable Integer perfilSocioId,
            @PathVariable String estado) {
        List<SolicitudCitaResponseDTO> solicitudes = 
            solicitudService.obtenerSolicitudesPorEstado(perfilSocioId, estado);
        return ResponseEntity.ok(solicitudes);
    }
    
    /**
     * PATCH /api/clinica/solicitudes-cita/{solicitudId}/contactado
     * 
     * Marca una solicitud como contactada.
     * Se ejecuta cuando el psicólogo hace clic en el botón de WhatsApp.
     */
    @PatchMapping("/{solicitudId}/contactado")
    public ResponseEntity<SolicitudCitaResponseDTO> marcarComoContactado(
            @PathVariable Integer solicitudId) {
        SolicitudCitaResponseDTO solicitud = solicitudService.marcarComoContactado(solicitudId);
        return ResponseEntity.ok(solicitud);
    }
    
    /**
     * PATCH /api/clinica/solicitudes-cita/{solicitudId}/convertido
     * 
     * Marca una solicitud como convertida (se volvió una cita real).
     * Normalmente se ejecuta automáticamente al usar el endpoint de conversión.
     */
    @PatchMapping("/{solicitudId}/convertido")
    public ResponseEntity<SolicitudCitaResponseDTO> marcarComoConvertido(
            @PathVariable Integer solicitudId) {
        SolicitudCitaResponseDTO solicitud = solicitudService.marcarComoConvertido(solicitudId);
        return ResponseEntity.ok(solicitud);
    }
    
    /**
     * PATCH /api/clinica/solicitudes-cita/{solicitudId}/descartado
     * 
     * Marca una solicitud como descartada.
     * 
     * QUERY PARAMS:
     * - motivo (opcional): Razón del descarte
     */
    @PatchMapping("/{solicitudId}/descartado")
    public ResponseEntity<SolicitudCitaResponseDTO> marcarComoDescartado(
            @PathVariable Integer solicitudId,
            @RequestParam(required = false) String motivo) {
        SolicitudCitaResponseDTO solicitud = solicitudService.marcarComoDescartado(solicitudId, motivo);
        return ResponseEntity.ok(solicitud);
    }
    
    /**
     * GET /api/clinica/solicitudes-cita/psicologo/{perfilSocioId}/pendientes/count
     * 
     * Cuenta solicitudes pendientes.
     * Útil para badge "Tienes 3 solicitudes nuevas".
     */
    @GetMapping("/psicologo/{perfilSocioId}/pendientes/count")
    public ResponseEntity<Map<String, Long>> contarSolicitudesPendientes(
            @PathVariable Integer perfilSocioId) {
        long cantidad = solicitudService.contarSolicitudesPendientes(perfilSocioId);
        Map<String, Long> response = new HashMap<>();
        response.put("pendientes", cantidad);
        return ResponseEntity.ok(response);
    }
    
    /**
     * PATCH /api/clinica/solicitudes-cita/{solicitudId}/nota
     * 
     * Actualiza la nota interna de una solicitud.
     * 
     * BODY:
     * {
     *   "nota": "Le escribí y quedamos en hablar el lunes"
     * }
     */
    @PatchMapping("/{solicitudId}/nota")
    public ResponseEntity<SolicitudCitaResponseDTO> actualizarNotaInterna(
            @PathVariable Integer solicitudId,
            @RequestBody Map<String, String> body) {
        String nota = body.get("nota");
        SolicitudCitaResponseDTO solicitud = solicitudService.actualizarNotaInterna(solicitudId, nota);
        return ResponseEntity.ok(solicitud);
    }
}

package app.clinica.service;

import app.clinica.dto.SolicitudCitaCreateDTO;
import app.clinica.dto.SolicitudCitaResponseDTO;
import java.util.List;

/**
 * Servicio para gestión de solicitudes de cita (leads desde landing page).
 */
public interface SolicitudCitaService {
    
    /**
     * Registra una nueva solicitud de cita desde la landing page pública.
     * Estado inicial: PENDIENTE.
     * 
     * @param perfilSocioId ID del psicólogo al que se solicita la cita
     * @param dto Datos del interesado
     * @return La solicitud creada
     */
    SolicitudCitaResponseDTO registrarSolicitud(Integer perfilSocioId, SolicitudCitaCreateDTO dto);
    
    /**
     * Obtiene todas las solicitudes recibidas por un psicólogo.
     * 
     * @param perfilSocioId ID del psicólogo
     * @return Lista de solicitudes ordenadas por fecha (más recientes primero)
     */
    List<SolicitudCitaResponseDTO> obtenerSolicitudesPorPsicologo(Integer perfilSocioId);
    
    /**
     * Filtra solicitudes por estado.
     * 
     * @param perfilSocioId ID del psicólogo
     * @param estado PENDIENTE, CONTACTADO, CONVERTIDO, DESCARTADO
     * @return Lista filtrada de solicitudes
     */
    List<SolicitudCitaResponseDTO> obtenerSolicitudesPorEstado(Integer perfilSocioId, String estado);
    
    /**
     * Marca una solicitud como contactada (el psicólogo abrió el WhatsApp).
     * 
     * @param solicitudId ID de la solicitud
     * @return Solicitud actualizada
     */
    SolicitudCitaResponseDTO marcarComoContactado(Integer solicitudId);
    
    /**
     * Marca una solicitud como convertida (se volvió una cita real).
     * 
     * @param solicitudId ID de la solicitud
     * @return Solicitud actualizada
     */
    SolicitudCitaResponseDTO marcarComoConvertido(Integer solicitudId);
    
    /**
     * Marca una solicitud como descartada.
     * 
     * @param solicitudId ID de la solicitud
     * @param motivo Razón del descarte (opcional)
     * @return Solicitud actualizada
     */
    SolicitudCitaResponseDTO marcarComoDescartado(Integer solicitudId, String motivo);
    
    /**
     * Cuenta solicitudes pendientes para un psicólogo.
     * Útil para badge "Tienes 3 solicitudes nuevas".
     * 
     * @param perfilSocioId ID del psicólogo
     * @return Cantidad de solicitudes pendientes
     */
    long contarSolicitudesPendientes(Integer perfilSocioId);
    
    /**
     * Actualiza la nota interna de una solicitud.
     * 
     * @param solicitudId ID de la solicitud
     * @param nota Nota del psicólogo (ej: "Le escribí y quedamos en hablar el lunes")
     * @return Solicitud actualizada
     */
    SolicitudCitaResponseDTO actualizarNotaInterna(Integer solicitudId, String nota);
}

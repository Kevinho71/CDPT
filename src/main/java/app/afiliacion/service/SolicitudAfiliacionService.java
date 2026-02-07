package app.afiliacion.service;

import app.afiliacion.dto.SolicitudAfiliacionCreateDTO;
import app.afiliacion.dto.SolicitudAfiliacionResponseDTO;
import app.afiliacion.dto.SolicitudAfiliacionUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SolicitudAfiliacionService {
    
    List<SolicitudAfiliacionResponseDTO> findAll();
    
    List<SolicitudAfiliacionResponseDTO> findByEstadoAfiliacion(String estadoAfiliacion);
    
    SolicitudAfiliacionResponseDTO findById(Integer id);
    
    SolicitudAfiliacionResponseDTO create(SolicitudAfiliacionCreateDTO dto, 
                                          MultipartFile fotoCarnetAnverso,
                                          MultipartFile fotoCarnetReverso,
                                          MultipartFile fotoTituloProvisicion,
                                          MultipartFile cv);
    
    SolicitudAfiliacionResponseDTO update(Integer id, SolicitudAfiliacionUpdateDTO dto);
    
    void delete(Integer id);
    
    /**
     * Crea una solicitud de afiliación validando primero el token
     * de la solicitud inicial y que no haya expirado
     */
    SolicitudAfiliacionResponseDTO createConToken(String token,
                                                   SolicitudAfiliacionCreateDTO dto,
                                                   MultipartFile fotoCarnetAnverso,
                                                   MultipartFile fotoCarnetReverso,
                                                   MultipartFile fotoTituloProvisicion,
                                                   MultipartFile cv);
    
    /**
     * Aprueba la solicitud y crea automáticamente:
     * - Persona
     * - Socio
     * - Perfil Público
     * - Estado de Cuenta (matrícula)
     */
    void aprobarSolicitud(Integer solicitudId);
}

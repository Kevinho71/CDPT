package app.afiliacion.service;

import app.afiliacion.dto.SolicitudInicialAfiliacionCreateDTO;
import app.afiliacion.dto.SolicitudInicialAfiliacionResponseDTO;
import app.afiliacion.dto.SolicitudInicialAfiliacionUpdateDTO;

import java.util.List;

public interface SolicitudInicialAfiliacionService {
    
    List<SolicitudInicialAfiliacionResponseDTO> findAll();
    
    List<SolicitudInicialAfiliacionResponseDTO> findByEstado(String estado);
    
    SolicitudInicialAfiliacionResponseDTO findById(Integer id);
    
    SolicitudInicialAfiliacionResponseDTO findByToken(String token);
    
    SolicitudInicialAfiliacionResponseDTO create(SolicitudInicialAfiliacionCreateDTO dto);
    
    SolicitudInicialAfiliacionResponseDTO update(Integer id, SolicitudInicialAfiliacionUpdateDTO dto);
    
    void delete(Integer id);
    
    /**
     * Genera un token único y cambia el estado a LINK_ENVIADO
     * para que el interesado pueda completar su solicitud completa
     */
    SolicitudInicialAfiliacionResponseDTO generarLinkInscripcion(Integer id);
}

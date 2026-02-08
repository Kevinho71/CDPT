package app.perfil.service;

import app.perfil.dto.*;
import app.perfil.dto.SocioDocumentoCompleteDTO;
import app.perfil.dto.SocioDocumentoCreateDTO;
import app.perfil.dto.SocioDocumentoResponseDTO;
import app.perfil.dto.SocioDocumentoUpdateDTO;
import app.perfil.dto.SocioDocumentoUploadDTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SocioDocumentoService {
    
    List<SocioDocumentoResponseDTO> findAll();
    
    List<SocioDocumentoResponseDTO> findByPerfilSocio(Integer perfilSocioId);
    
    List<SocioDocumentoResponseDTO> findByPerfilSocioVisible(Integer perfilSocioId);
    
    SocioDocumentoResponseDTO findById(Integer id);
    
    SocioDocumentoResponseDTO create(SocioDocumentoCreateDTO dto);
    
    SocioDocumentoResponseDTO update(Integer id, SocioDocumentoUpdateDTO dto);
    
    void delete(Integer id);
    
    // Nuevos métodos para gestión desde el perfil del socio
    
    /**
     * Crea un nuevo documento y lo asocia al perfil del socio
     * @param perfilSocioId ID del perfil del socio
     * @param dto DTO con la información del documento
     * @return DTO completo con toda la información
     */
    SocioDocumentoCompleteDTO uploadDocumento(Integer perfilSocioId, SocioDocumentoUploadDTO dto);
    
    /**
     * Lista documentos de un perfil ordenados
     * @param perfilSocioId ID del perfil del socio
     * @param soloVisibles true para obtener solo los visibles
     * @return Lista de documentos ordenados
     */
    List<SocioDocumentoCompleteDTO> findDocumentosByPerfil(Integer perfilSocioId, boolean soloVisibles);
    
    /**
     * Elimina un documento del socio (soft delete en la relación)
     * @param perfilSocioId ID del perfil del socio (validación)
     * @param socioDocumentoId ID de la relación socio_documentos
     */
    void deleteDocumento(Integer perfilSocioId, Integer socioDocumentoId);
}

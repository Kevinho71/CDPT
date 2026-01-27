package app.perfil.service;

import app.perfil.dto.PerfilSocioDTO;
import app.perfil.dto.PerfilSocioResponseDTO;
import app.perfil.entity.PerfilSocioEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PerfilSocioService {
    
    /**
     * Crea o actualiza un perfil de socio
     */
    PerfilSocioResponseDTO createOrUpdatePerfil(PerfilSocioDTO dto, MultipartFile fotoPerfil, MultipartFile fotoBanner);
    
    /**
     * Obtiene un perfil por ID
     */
    PerfilSocioResponseDTO findByIdDTO(Integer id);
    
    /**
     * Obtiene un perfil por ID del socio
     */
    PerfilSocioResponseDTO findBySocioIdDTO(Integer socioId);
    
    /**
     * Lista todos los perfiles
     */
    List<PerfilSocioResponseDTO> findAllDTO();
    
    /**
     * Lista perfiles por estado
     */
    List<PerfilSocioResponseDTO> findAllDTO(Integer estado);
    
    /**
     * Lista perfiles públicos
     */
    List<PerfilSocioResponseDTO> findPublicProfiles();
    
    /**
     * Cambia el estado de un perfil
     */
    PerfilSocioResponseDTO changeStatusPerfil(Integer id);
    
    /**
     * Incrementa las visualizaciones de un perfil
     */
    void incrementarVisualizaciones(Integer id);
    
    /**
     * Busca perfiles por ciudad
     */
    List<PerfilSocioResponseDTO> findByCiudad(String ciudad);
    
    /**
     * Elimina la foto de perfil
     */
    PerfilSocioResponseDTO deleteFotoPerfil(Integer id);
    
    /**
     * Elimina la foto de banner
     */
    PerfilSocioResponseDTO deleteFotoBanner(Integer id);
}

package app.socio.service;

import app.socio.dto.SocioCompleteResponseDTO;
import app.socio.dto.SocioResponseDTO;
import app.socio.entity.SocioEntity;
import java.util.List;

/**
 * Servicio independiente para Socio
 * No extiende de ningún servicio genérico
 */
public interface SocioService {
  
  // ============================================
  // CRUD Básico
  // ============================================
  
  /**
   * Lista todos los socios (entidades completas)
   */
  List<SocioEntity> findAll() throws Exception;
  
  /**
   * Busca un socio por ID
   */
  SocioEntity findById(Integer id) throws Exception;
  
  /**
   * Guarda un nuevo socio
   */
  SocioEntity save(SocioEntity socio) throws Exception;
  
  /**
   * Actualiza un socio existente
   */
  SocioEntity update(Integer id, SocioEntity socio) throws Exception;
  
  /**
   * Elimina un socio
   */
  boolean delete(Integer id) throws Exception;
  
  // ============================================
  // Métodos específicos de negocio
  // ============================================
  
  int getIdPrimaryKey() throws Exception;
  
  Integer getCodigo() throws Exception;
    
  void updateStatus(int status, int id) throws Exception;
  
  Integer getTotAll(String search, int estado) throws Exception;
  
  SocioEntity findByNrodocumento(String nrodocumento) throws Exception;
  
  SocioEntity updatecatalogos(Integer id, SocioEntity socio) throws Exception;
  
  // ============================================
  // Métodos con DTOs
  // ============================================
  
  /**
   * Lista todos los socios (versión DTO simplificada)
   */
  List<SocioResponseDTO> findAllDTO();
  
  /**
   * Busca un socio por ID (versión DTO)
   */
  SocioResponseDTO findByIdDTO(Integer id);
  
  /**
   * Busca un socio por número de documento (versión DTO)
   */
  SocioResponseDTO findByNrodocumentoDTO(String nrodocumento);
  
  /**
   * Actualiza los catálogos de un socio (versión DTO)
   */
  SocioResponseDTO updateCatalogosDTO(Integer id, List<Integer> catalogIds);
  
  /**
   * Lista completa de socios con toda su información (usuario, empresas, etc.)
   */
  List<SocioCompleteResponseDTO> findAllComplete();
}
package app.perfil.controller;

import app.common.util.ArchivoService;
import app.common.util.Constantes;
import app.perfil.dto.PerfilSocioDTO;
import app.perfil.dto.PerfilSocioResponseDTO;
import app.perfil.dto.SocioIdiomaDTO;
import app.perfil.service.PerfilSocioService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.util.List;

/**
 * REST Controller para gestión de perfiles de socios
 * Base path: /api/perfiles
 */
@RestController
@RequestMapping("/api/perfiles")
public class PerfilSocioController {
    
    @Autowired
    private PerfilSocioService perfilSocioService;
    
    @Autowired
    private ArchivoService archivoService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Lista todos los perfiles
     * GET /api/perfiles
     */
    @GetMapping
    public ResponseEntity<List<PerfilSocioResponseDTO>> getAll() {
        return ResponseEntity.ok(perfilSocioService.findAllDTO());
    }
    
    /**
     * Lista perfiles por estado
     * GET /api/perfiles?estado=1
     */
    @GetMapping(params = "estado")
    public ResponseEntity<List<PerfilSocioResponseDTO>> getAllByEstado(@RequestParam Integer estado) {
        return ResponseEntity.ok(perfilSocioService.findAllDTO(estado));
    }
    
    /**
     * Lista perfiles públicos
     * GET /api/perfiles/publicos
     */
    @GetMapping("/publicos")
    public ResponseEntity<List<PerfilSocioResponseDTO>> getPublicProfiles() {
        return ResponseEntity.ok(perfilSocioService.findPublicProfiles());
    }
    
    /**
     * Busca perfiles por ciudad
     * GET /api/perfiles/ciudad?q=La Paz
     */
    @GetMapping("/ciudad")
    public ResponseEntity<List<PerfilSocioResponseDTO>> getByCiudad(@RequestParam String q) {
        return ResponseEntity.ok(perfilSocioService.findByCiudad(q));
    }
    
    /**
     * Obtiene un perfil por ID
     * GET /api/perfiles/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PerfilSocioResponseDTO> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(perfilSocioService.findByIdDTO(id));
    }
    
    /**
     * Obtiene un perfil por ID del socio
     * GET /api/perfiles/socio/{socioId}
     */
    @GetMapping("/socio/{socioId}")
    public ResponseEntity<PerfilSocioResponseDTO> getBySocioId(@PathVariable Integer socioId) {
        return ResponseEntity.ok(perfilSocioService.findBySocioIdDTO(socioId));
    }
    
    /**
     * Crea o actualiza un perfil de socio con fotos
     * POST /api/perfiles (multipart/form-data)
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PerfilSocioResponseDTO> createOrUpdate(
            @RequestParam Integer socioId,
            @RequestParam(required = false) String tituloProfesional,
            @RequestParam(required = false) Integer anosExperiencia,
            @RequestParam(required = false) String resumenProfesional,
            @RequestParam(required = false) String modalidadTrabajo,
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false) String zona,
            @RequestParam(required = false) String linkedinUrl,
            @RequestParam(required = false) String facebookUrl,
            @RequestParam(required = false) String twitterUrl,
            @RequestParam(required = false) String instagramUrl,
            @RequestParam(required = false) String whatsapp,
            @RequestParam(required = false) String sitioWeb,
            @RequestParam(required = false) Boolean perfilPublico,
            @RequestParam(required = false) Boolean permiteContacto,
            @RequestParam(required = false) Integer estado,
            @RequestParam(required = false) String idiomas,
            @RequestParam(required = false) String sectorIds,
            @RequestParam(required = false) String servicioIds,
            @RequestParam(required = false) String especialidadIds,
            @RequestParam(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
            @RequestParam(value = "fotoBanner", required = false) MultipartFile fotoBanner) {
        
        try {
            // Construir DTO manualmente
            PerfilSocioDTO perfilDTO = new PerfilSocioDTO();
            perfilDTO.setSocioId(socioId);
            perfilDTO.setTituloProfesional(tituloProfesional);
            perfilDTO.setAnosExperiencia(anosExperiencia);
            perfilDTO.setResumenProfesional(resumenProfesional);
            perfilDTO.setModalidadTrabajo(modalidadTrabajo);
            perfilDTO.setCiudad(ciudad);
            perfilDTO.setZona(zona);
            perfilDTO.setLinkedinUrl(linkedinUrl);
            perfilDTO.setFacebookUrl(facebookUrl);
            perfilDTO.setTwitterUrl(twitterUrl);
            perfilDTO.setInstagramUrl(instagramUrl);
            perfilDTO.setWhatsapp(whatsapp);
            perfilDTO.setSitioWeb(sitioWeb);
            perfilDTO.setPerfilPublico(perfilPublico != null ? perfilPublico : true);
            perfilDTO.setPermiteContacto(permiteContacto != null ? permiteContacto : true);
            perfilDTO.setEstado(estado != null ? estado : 1);
            
            // Parsear arrays JSON
            if (idiomas != null && !idiomas.trim().isEmpty()) {
                perfilDTO.setIdiomas(objectMapper.readValue(idiomas, new TypeReference<List<SocioIdiomaDTO>>(){}));
            }
            
            if (sectorIds != null && !sectorIds.trim().isEmpty()) {
                perfilDTO.setSectorIds(objectMapper.readValue(sectorIds, new TypeReference<List<Integer>>(){}));
            }
            
            if (servicioIds != null && !servicioIds.trim().isEmpty()) {
                perfilDTO.setServicioIds(objectMapper.readValue(servicioIds, new TypeReference<List<Integer>>(){}));
            }
            
            if (especialidadIds != null && !especialidadIds.trim().isEmpty()) {
                perfilDTO.setEspecialidadIds(objectMapper.readValue(especialidadIds, new TypeReference<List<Integer>>(){}));
            }
            
            PerfilSocioResponseDTO result = perfilSocioService.createOrUpdatePerfil(perfilDTO, fotoPerfil, fotoBanner);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
            
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Error en el formato JSON. Verifica que los arrays no tengan comas dobles o elementos vacíos. Detalle: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar los datos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Actualiza un perfil existente (también acepta multipart)
     * PUT /api/perfiles/{id} (multipart/form-data)
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PerfilSocioResponseDTO> update(
            @PathVariable Integer id,
            @RequestParam Integer socioId,
            @RequestParam(required = false) String tituloProfesional,
            @RequestParam(required = false) Integer anosExperiencia,
            @RequestParam(required = false) String resumenProfesional,
            @RequestParam(required = false) String modalidadTrabajo,
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false) String zona,
            @RequestParam(required = false) String linkedinUrl,
            @RequestParam(required = false) String facebookUrl,
            @RequestParam(required = false) String twitterUrl,
            @RequestParam(required = false) String instagramUrl,
            @RequestParam(required = false) String whatsapp,
            @RequestParam(required = false) String sitioWeb,
            @RequestParam(required = false) Boolean perfilPublico,
            @RequestParam(required = false) Boolean permiteContacto,
            @RequestParam(required = false) Integer estado,
            @RequestParam(required = false) String idiomas,
            @RequestParam(required = false) String sectorIds,
            @RequestParam(required = false) String servicioIds,
            @RequestParam(required = false) String especialidadIds,
            @RequestParam(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
            @RequestParam(value = "fotoBanner", required = false) MultipartFile fotoBanner) {
        
        try {
            // Verificar que el perfil existe
            perfilSocioService.findByIdDTO(id);
            
            // Construir DTO manualmente
            PerfilSocioDTO perfilDTO = new PerfilSocioDTO();
            perfilDTO.setSocioId(socioId);
            perfilDTO.setTituloProfesional(tituloProfesional);
            perfilDTO.setAnosExperiencia(anosExperiencia);
            perfilDTO.setResumenProfesional(resumenProfesional);
            perfilDTO.setModalidadTrabajo(modalidadTrabajo);
            perfilDTO.setCiudad(ciudad);
            perfilDTO.setZona(zona);
            perfilDTO.setLinkedinUrl(linkedinUrl);
            perfilDTO.setFacebookUrl(facebookUrl);
            perfilDTO.setTwitterUrl(twitterUrl);
            perfilDTO.setInstagramUrl(instagramUrl);
            perfilDTO.setWhatsapp(whatsapp);
            perfilDTO.setSitioWeb(sitioWeb);
            perfilDTO.setPerfilPublico(perfilPublico != null ? perfilPublico : true);
            perfilDTO.setPermiteContacto(permiteContacto != null ? permiteContacto : true);
            perfilDTO.setEstado(estado != null ? estado : 1);
            
            // Parsear arrays JSON
            if (idiomas != null && !idiomas.trim().isEmpty()) {
                perfilDTO.setIdiomas(objectMapper.readValue(idiomas, new TypeReference<List<SocioIdiomaDTO>>(){}));
            }
            
            if (sectorIds != null && !sectorIds.trim().isEmpty()) {
                perfilDTO.setSectorIds(objectMapper.readValue(sectorIds, new TypeReference<List<Integer>>(){}));
            }
            
            if (servicioIds != null && !servicioIds.trim().isEmpty()) {
                perfilDTO.setServicioIds(objectMapper.readValue(servicioIds, new TypeReference<List<Integer>>(){}));
            }
            
            if (especialidadIds != null && !especialidadIds.trim().isEmpty()) {
                perfilDTO.setEspecialidadIds(objectMapper.readValue(especialidadIds, new TypeReference<List<Integer>>(){}));
            }
            
            PerfilSocioResponseDTO result = perfilSocioService.createOrUpdatePerfil(perfilDTO, fotoPerfil, fotoBanner);
            return ResponseEntity.ok(result);
            
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Error en el formato JSON. Verifica que los arrays no tengan comas dobles o elementos vacíos. Detalle: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar los datos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Cambia el estado de un perfil (activo/inactivo)
     * PATCH /api/perfiles/{id}/status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<PerfilSocioResponseDTO> changeStatus(@PathVariable Integer id) {
        PerfilSocioResponseDTO result = perfilSocioService.changeStatusPerfil(id);
        return ResponseEntity.ok(result);
    }
    
    /**
     * Elimina la foto de perfil
     * DELETE /api/perfiles/{id}/foto-perfil
     */
    @DeleteMapping("/{id}/foto-perfil")
    public ResponseEntity<PerfilSocioResponseDTO> deleteFotoPerfil(@PathVariable Integer id) {
        PerfilSocioResponseDTO result = perfilSocioService.deleteFotoPerfil(id);
        return ResponseEntity.ok(result);
    }
    
    /**
     * Elimina la foto de banner
     * DELETE /api/perfiles/{id}/foto-banner
     */
    @DeleteMapping("/{id}/foto-banner")
    public ResponseEntity<PerfilSocioResponseDTO> deleteFotoBanner(@PathVariable Integer id) {
        PerfilSocioResponseDTO result = perfilSocioService.deleteFotoBanner(id);
        return ResponseEntity.ok(result);
    }
    
    /**
     * Obtiene la foto de perfil de un socio (redirige a Cloudinary)
     * GET /api/perfiles/foto-perfil/{filename}
     * DEPRECATED: Las imágenes ahora se acceden directamente desde Cloudinary mediante URLs en el DTO
     */
    @GetMapping("/foto-perfil/{filename}")
    public ResponseEntity<String> getFotoPerfil(@PathVariable String filename) {
        // Ahora las imágenes están en Cloudinary y se acceden mediante URLs directas
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
            .body("Este endpoint está deprecated. Use la URL de Cloudinary que viene en el DTO del perfil.");
    }
    
    /**
     * Obtiene la foto de banner de un socio (redirige a Cloudinary)
     * GET /api/perfiles/foto-banner/{filename}
     * DEPRECATED: Las imágenes ahora se acceden directamente desde Cloudinary mediante URLs en el DTO
     */
    @GetMapping("/foto-banner/{filename}")
    public ResponseEntity<String> getFotoBanner(@PathVariable String filename) {
        // Ahora las imágenes están en Cloudinary y se acceden mediante URLs directas
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
            .body("Este endpoint está deprecated. Use la URL de Cloudinary que viene en el DTO del perfil.");
    }
    
    // Método serveFile eliminado - Las imágenes ahora se sirven directamente desde Cloudinary
}

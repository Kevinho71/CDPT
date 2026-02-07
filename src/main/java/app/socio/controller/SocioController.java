 package app.socio.controller;

import app.socio.dto.SocioCompleteResponseDTO;
import app.socio.dto.SocioDTO;
import app.socio.dto.SocioResponseDTO;
import app.socio.entity.SocioEntity;
import app.common.exception.InvalidDataException;
import app.common.util.ArchivoService;
import app.socio.service.SocioServiceImpl;
import app.common.util.Constantes;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST Controller para gestión de socios (partners)
 * Base path: /api/partners
 */
@RestController
@RequestMapping("/api/partners")
public class SocioController {

    @Autowired
    private SocioServiceImpl socioService;

    @Autowired
    private ArchivoService archivoService;

    /**
     * Lista todos los socios (simple)
     * GET /api/partners
     */
    @GetMapping
    public ResponseEntity<List<SocioResponseDTO>> getAll() {
        return ResponseEntity.ok(socioService.findAllDTO());
    }

    /**
     * Lista todos los socios con información completa (usuario y empresas)
     * GET /api/partners/complete
     * Retorna: socio + usuario (si existe) + empresas/catálogos asociadas
     */
    @GetMapping("/complete")
    public ResponseEntity<List<SocioCompleteResponseDTO>> getAllComplete() {
        return ResponseEntity.ok(socioService.findAllComplete());
    }
 
    /**
     * Obtiene un socio por ID
     * GET /api/partners/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<SocioResponseDTO> getOne(@PathVariable Integer id) {
        SocioResponseDTO socio = socioService.findByIdDTO(id);
        return ResponseEntity.ok(socio);
    }

    /**
     * Crea un nuevo socio con logo
     * POST /api/partners (form-data para archivo)
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SocioResponseDTO> create(
            @ModelAttribute SocioDTO socioDTO,
            @RequestParam(value = "catalogoIds", required = false) List<Integer> catalogoIds,
            @RequestParam(value = "logo", required = false) MultipartFile logo) {
        
        // Asignar catalogoIds al DTO si se proporcionaron
        if (catalogoIds != null && !catalogoIds.isEmpty()) {
            socioDTO.setCatalogoIds(catalogoIds);
        }
        
        SocioResponseDTO result = socioService.createSocio(socioDTO, logo);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * Actualiza un socio existente con logo
     * PUT /api/partners/{id} (form-data para archivo)
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SocioResponseDTO> update(
            @PathVariable Integer id,
            @ModelAttribute SocioDTO socioDTO,
            @RequestParam(value = "catalogoIds", required = false) List<Integer> catalogoIds,
            @RequestParam(value = "logo", required = false) MultipartFile logo) {
        
        // Asignar catalogoIds al DTO si se proporcionaron
        if (catalogoIds != null && !catalogoIds.isEmpty()) {
            socioDTO.setCatalogoIds(catalogoIds);
        }
        
        SocioResponseDTO result = socioService.updateSocio(id, socioDTO, logo);
        return ResponseEntity.ok(result);
    }

    /**
     * Actualiza solo catálogos del socio
     * PATCH /api/partners/{id}/catalogs
     */
    @PatchMapping("/{id}/catalogs")
    public ResponseEntity<SocioResponseDTO> updateCatalogs(
            @PathVariable Integer id,
            @RequestBody Map<String, List<Integer>> body) {
        
        List<Integer> catalogIds = body.get("catalogIds");
        if (catalogIds == null || catalogIds.isEmpty()) {
            throw new app.common.exception.InvalidDataException("Lista de catálogos es requerida");
        }
        
        SocioResponseDTO result = socioService.updateCatalogosDTO(id, catalogIds);
        return ResponseEntity.ok(result);
    }

    /**
     * Cambia el estado (activo/inactivo) de un socio
     * PATCH /api/partners/{id}/status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<SocioResponseDTO> updateStatus(
            @PathVariable Integer id,
            @RequestParam (value= "estado") Integer newStatus) {
        
        if (newStatus == null) {
            throw new InvalidDataException("Campo 'estado' es requerido");
        }
        
        SocioResponseDTO result = socioService.changeStatus(id, newStatus);
        return ResponseEntity.ok(result);
    }

    /**
     * Busca un socio por número de documento
     * GET /api/partners/document/{documentNumber}
     */
    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<SocioResponseDTO> findByDocument(@PathVariable String documentNumber) {
        SocioResponseDTO socio = socioService.findByNrodocumentoDTO(documentNumber);
        return ResponseEntity.ok(socio);
    }

    /**
     * Obtiene el logo de un socio (redirige a Cloudinary)
     * GET /api/partners/logo/{filename}
     * DEPRECATED: Las imágenes ahora se acceden directamente desde Cloudinary mediante URLs en el DTO
     */
    @GetMapping("/logo/{filename}")
    public ResponseEntity<String> getLogo(@PathVariable String filename) {
        // Ahora las imágenes están en Cloudinary y se acceden mediante URLs directas
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
            .body("Este endpoint está deprecated. Use la URL de Cloudinary que viene en el DTO del socio.");
    }



    // Método serveFile eliminado - Las imágenes ahora se sirven directamente desde Cloudinary
}   
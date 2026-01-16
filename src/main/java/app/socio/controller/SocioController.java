 package app.socio.controller;

import app.socio.dto.SocioCompleteResponseDTO;
import app.socio.dto.SocioDTO;
import app.socio.dto.SocioResponseDTO;
import app.socio.entity.SocioEntity;
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
     * Lista socios con paginación y filtros (DataTables)
     * GET /api/partners/datatable
     */
    @GetMapping("/datatable")
    public ResponseEntity<Map<String, Object>> getDataTable(
            HttpServletRequest request,
            @Param("draw") int draw,
            @Param("length") int length,
            @Param("start") int start,
            @Param("estado") int estado) {
        
        String search = request.getParameter("search[value]");
        Map<String, Object> result = socioService.getDataTableData(draw, length, start, estado, search);
        return ResponseEntity.ok(result);
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
            @RequestBody Map<String, Integer> body) {
        
        Integer newStatus = body.get("estado");
        if (newStatus == null) {
            throw new app.common.exception.InvalidDataException("Campo 'estado' es requerido");
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
     * Renueva el código QR de un socio
     * POST /api/partners/{id}/qr/renew
     */
    @PostMapping("/{id}/qr/renew")
    public ResponseEntity<SocioResponseDTO> renewQR(@PathVariable Integer id) {
        SocioResponseDTO result = socioService.renovarQRDTO(id);
        return ResponseEntity.ok(result);
    }

    /**
     * Obtiene el logo de un socio
     * GET /api/partners/logo/{filename}
     */
    @GetMapping("/logo/{filename}")
    public ResponseEntity<Resource> getLogo(@PathVariable String filename) {
        return serveFile(filename, Constantes.nameFolderLogoSocio, "Logo");
    }

    /**
     * Obtiene el código QR de un socio
     * GET /api/partners/qr/{filename}
     */
    @GetMapping("/qr/{filename}")
    public ResponseEntity<Resource> getQR(@PathVariable String filename) {
        return serveFile(filename, Constantes.nameFolderQrSocio, "QR");
    }

    /**
     * Método privado para servir archivos (logos, QRs) con validación y cache
     */
    private ResponseEntity<Resource> serveFile(String filename, String folder, String fileType) {
        // Validar filename (seguridad: prevenir path traversal)
        if (filename == null || filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            throw new app.common.exception.InvalidDataException("Nombre de archivo inválido");
        }

        try {
            java.nio.file.Path filePath = archivoService.linkArchivo(folder, filename);

            if (filePath == null || !Files.exists(filePath)) {
                throw new app.common.exception.ResourceNotFoundException(fileType, "filename", filename);
            }

            // Usar PathResource en lugar de InputStreamResource (más eficiente)
            org.springframework.core.io.PathResource resource = 
                new org.springframework.core.io.PathResource(filePath);

            // Determinar Content-Type
            String contentType;
            try {
                contentType = Files.probeContentType(filePath);
            } catch (IOException e) {
                contentType = null;
            }
            
            if (contentType == null) {
                // Default para imágenes comunes
                if (filename.toLowerCase().endsWith(".png")) {
                    contentType = "image/png";
                } else if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg")) {
                    contentType = "image/jpeg";
                } else {
                    contentType = "application/octet-stream";
                }
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header("Content-Disposition", "inline; filename=\"" + filename + "\"")
                    .cacheControl(org.springframework.http.CacheControl.maxAge(7, java.util.concurrent.TimeUnit.DAYS))
                    .body(resource);

        } catch (Exception e) {
            throw new app.common.exception.FileStorageException("Error al leer el archivo de " + fileType, e);
        }
    }
}   
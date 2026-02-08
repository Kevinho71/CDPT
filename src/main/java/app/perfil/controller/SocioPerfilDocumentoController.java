package app.perfil.controller;

import app.perfil.service.SocioDocumentoService;
import app.perfil.dto.SocioDocumentoCompleteDTO;
import app.perfil.dto.SocioDocumentoUploadDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controller para gestión de documentos profesionales desde el perfil del socio
 * Permite que los psicólogos suban, organicen y gestionen sus certificados/diplomas
 * 
 * Endpoints principales:
 * - POST /api/socio/perfil/{perfilId}/documentos - Subir nuevo documento
 * - GET /api/socio/perfil/{perfilId}/documentos - Listar todos los documentos del socio
 * - PUT /api/socio/perfil/{perfilId}/documentos/{docId} - Actualizar documento
 * - DELETE /api/socio/perfil/{perfilId}/documentos/{docId} - Eliminar documento
 * 
 * Endpoint público:
 * - GET /api/publico/perfil/{perfilId}/documentos - Ver documentos visibles (para visitantes)
 */
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SocioPerfilDocumentoController {
    
    @Autowired
    private SocioDocumentoService socioDocumentoService;
    
    /**
     * Subir un nuevo documento profesional desde el dashboard del socio
     * El archivo se sube a SOCIO_DOCUMENTOS_PERFIL en Cloudinary
     * 
     * @param perfilId ID del perfil del socio
     * @param titulo Título del documento (ej: "Diplomado en Terapia Cognitiva")
     * @param descripcion Descripción opcional del documento
     * @param archivo Archivo PDF o imagen (JPG/PNG)
     * @param orden Orden de visualización (opcional, default: 0)
     * @param esVisible Si es visible públicamente (opcional, default: true)
     * @return El documento creado con su URL
     */
    @PostMapping(value = "/api/socio/perfil/{perfilId}/documentos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SocioDocumentoCompleteDTO> subirDocumento(
            @PathVariable Integer perfilId,
            @RequestParam("titulo") String titulo,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam(value = "orden", required = false, defaultValue = "0") Integer orden,
            @RequestParam(value = "esVisible", required = false, defaultValue = "true") Boolean esVisible) {
        
        // Construir DTO
        SocioDocumentoUploadDTO dto = new SocioDocumentoUploadDTO();
        dto.setTitulo(titulo);
        dto.setDescripcion(descripcion);
        dto.setArchivo(archivo);
        dto.setOrden(orden);
        dto.setEsVisible(esVisible);
        
        SocioDocumentoCompleteDTO response = socioDocumentoService.uploadDocumento(perfilId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Listar todos los documentos del socio (dashboard privado)
     * Incluye documentos visibles y ocultos
     * 
     * @param perfilId ID del perfil del socio
     * @return Lista de documentos ordenados
     */
    @GetMapping("/api/socio/perfil/{perfilId}/documentos")
    public ResponseEntity<List<SocioDocumentoCompleteDTO>> listarMisDocumentos(@PathVariable Integer perfilId) {
        List<SocioDocumentoCompleteDTO> documentos = socioDocumentoService.findDocumentosByPerfil(perfilId, false);
        return ResponseEntity.ok(documentos);
    }
    
    /**
     * Actualizar información de un documento
     * Permite cambiar título, descripción, visibilidad y orden
     * 
     * @param perfilId ID del perfil del socio
     * @param docId ID de la relación socio_documentos
     * @param dto DTO con los campos a actualizar
     * @return Documento actualizado
     */

    
    /**
     * Eliminar un documento del socio
     * Elimina la relación y el archivo de Cloudinary
     * 
     * @param perfilId ID del perfil del socio
     * @param docId ID de la relación socio_documentos
     * @return 204 No Content
     */
    @DeleteMapping("/api/socio/perfil/{perfilId}/documentos/{docId}")
    public ResponseEntity<Void> eliminarDocumento(
            @PathVariable Integer perfilId,
            @PathVariable Integer docId) {
        
        socioDocumentoService.deleteDocumento(perfilId, docId);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * ENDPOINT PÚBLICO: Ver documentos visibles de un psicólogo (Landing Page)
     * Solo retorna documentos con es_visible = true
     * Ordenados según el campo 'orden'
     * 
     * @param perfilId ID del perfil del socio
     * @return Lista de documentos visibles ordenados
     */
    @GetMapping("/api/publico/perfil/{perfilId}/documentos")
    public ResponseEntity<List<SocioDocumentoCompleteDTO>> verDocumentosPublicos(@PathVariable Integer perfilId) {
        List<SocioDocumentoCompleteDTO> documentos = socioDocumentoService.findDocumentosByPerfil(perfilId, true);
        return ResponseEntity.ok(documentos);
    }
}

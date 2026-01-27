package app.cms.controller;

import app.cms.dto.ContentCreateDTO;
import app.cms.dto.ContentDTO;
import app.cms.service.WebStaticContentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller ADMIN para gestión de contenidos estáticos
 * Requiere ROLE_ADMIN
 */
@RestController
@RequestMapping("/api/admin/content")
public class WebStaticContentAdminController {

    @Autowired
    private WebStaticContentService contentService;

    /**
     * Crea un nuevo contenido estático
     * POST /api/admin/content
     */
    @PostMapping
    public ResponseEntity<ContentDTO> createContent(
            @Valid @RequestBody ContentCreateDTO createDTO,
            Authentication authentication) {

        String usuarioEmail = authentication.getName();
        ContentDTO created = contentService.createContent(createDTO, usuarioEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza múltiples contenidos en lote (Batch Update)
     * PUT /api/admin/content
     * 
     * Request Body: { "home_hero_titulo": "Nuevo título", "footer_copy": "© 2026" }
     */
    @PutMapping
    public ResponseEntity<List<ContentDTO>> batchUpdateContent(
            @RequestBody Map<String, String> updates,
            Authentication authentication) {

        String usuarioEmail = authentication.getName();
        List<ContentDTO> updatedContents = contentService.batchUpdateContent(updates, usuarioEmail);
        return ResponseEntity.ok(updatedContents);
    }

    /**
     * Obtiene todos los contenidos como lista (para panel admin)
     * GET /api/admin/content
     */
    @GetMapping
    public ResponseEntity<List<ContentDTO>> getAllContentList() {
        List<ContentDTO> contents = contentService.getAllContentList();
        return ResponseEntity.ok(contents);
    }

    /**
     * Obtiene un contenido por su clave
     * GET /api/admin/content/{clave}
     */
    @GetMapping("/{clave}")
    public ResponseEntity<ContentDTO> getContentByClave(@PathVariable String clave) {
        ContentDTO content = contentService.getContentByClave(clave);
        return ResponseEntity.ok(content);
    }
}

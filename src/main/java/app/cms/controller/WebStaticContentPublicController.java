package app.cms.controller;

import app.cms.service.WebStaticContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller PÚBLICO para consumir contenidos estáticos
 * Sin autenticación requerida
 */
@RestController
public class WebStaticContentPublicController {

    @Autowired
    private WebStaticContentService contentService;

    /**
     * Obtiene todos los contenidos como Map clave-valor (para frontend público)
     * GET /api/public/content
     * GET /public/content
     * 
     * Response: { "home_hero_titulo": "Liderazgo...", "footer_copy": "© 2026", ... }
     */
    @GetMapping({"/api/public/content", "/public/content"})
    public ResponseEntity<Map<String, String>> getAllContentMap() {
        Map<String, String> contentMap = contentService.getAllContentMap();
        return ResponseEntity.ok(contentMap);
    }
}

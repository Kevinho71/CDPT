package app.perfil.controller;

import app.perfil.service.DocumentoProfesionalService;
import app.perfil.dto.DocumentoProfesionalCreateDTO;
import app.perfil.dto.DocumentoProfesionalResponseDTO;
import app.perfil.dto.DocumentoProfesionalUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Controller para gestión de documentos profesionales
 * Base path: /api/documentos-profesionales
 */
@RestController
@RequestMapping("/api/documentos-profesionales")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class DocumentoProfesionalController {
    
    @Autowired
    private DocumentoProfesionalService documentoProfesionalService;
    
    @GetMapping
    public ResponseEntity<List<DocumentoProfesionalResponseDTO>> findAll() {
        return ResponseEntity.ok(documentoProfesionalService.findAll());
    }
    
    @GetMapping("/tipo/{tipoDocumento}")
    public ResponseEntity<List<DocumentoProfesionalResponseDTO>> findByTipo(@PathVariable String tipoDocumento) {
        return ResponseEntity.ok(documentoProfesionalService.findByTipo(tipoDocumento));
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<DocumentoProfesionalResponseDTO>> findByEstado(@PathVariable Integer estado) {
        return ResponseEntity.ok(documentoProfesionalService.findByEstado(estado));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DocumentoProfesionalResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(documentoProfesionalService.findById(id));
    }
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentoProfesionalResponseDTO> create(
            @Valid @ModelAttribute DocumentoProfesionalCreateDTO dto,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo) {
        
        DocumentoProfesionalResponseDTO response = documentoProfesionalService.create(dto, archivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentoProfesionalResponseDTO> update(
            @PathVariable Integer id,
            @Valid @ModelAttribute DocumentoProfesionalUpdateDTO dto,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo) throws IOException {
        
        return ResponseEntity.ok(documentoProfesionalService.update(id, dto, archivo));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        documentoProfesionalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

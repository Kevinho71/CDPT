package app.perfil.controller;

import app.perfil.service.SocioDocumentoService;
import app.perfil.dto.SocioDocumentoCreateDTO;
import app.perfil.dto.SocioDocumentoResponseDTO;
import app.perfil.dto.SocioDocumentoUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gestión de relaciones entre socios y documentos
 * Base path: /api/socio-documentos
 */
@RestController
@RequestMapping("/api/socio-documentos")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SocioDocumentoController {
    
    @Autowired
    private SocioDocumentoService socioDocumentoService;
    
    @GetMapping
    public ResponseEntity<List<SocioDocumentoResponseDTO>> findAll() {
        return ResponseEntity.ok(socioDocumentoService.findAll());
    }
    
    @GetMapping("/perfil/{perfilSocioId}")
    public ResponseEntity<List<SocioDocumentoResponseDTO>> findByPerfilSocio(@PathVariable Integer perfilSocioId) {
        return ResponseEntity.ok(socioDocumentoService.findByPerfilSocio(perfilSocioId));
    }
    
    @GetMapping("/perfil/{perfilSocioId}/visibles")
    public ResponseEntity<List<SocioDocumentoResponseDTO>> findByPerfilSocioVisible(@PathVariable Integer perfilSocioId) {
        return ResponseEntity.ok(socioDocumentoService.findByPerfilSocioVisible(perfilSocioId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SocioDocumentoResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(socioDocumentoService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<SocioDocumentoResponseDTO> create(@Valid @RequestBody SocioDocumentoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(socioDocumentoService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SocioDocumentoResponseDTO> update(
            @PathVariable Integer id, 
            @Valid @RequestBody SocioDocumentoUpdateDTO dto) {
        return ResponseEntity.ok(socioDocumentoService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        socioDocumentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

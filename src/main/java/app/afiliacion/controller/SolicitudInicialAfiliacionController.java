package app.afiliacion.controller;

import app.afiliacion.dto.SolicitudInicialAfiliacionCreateDTO;
import app.afiliacion.dto.SolicitudInicialAfiliacionResponseDTO;
import app.afiliacion.dto.SolicitudInicialAfiliacionUpdateDTO;
import app.afiliacion.service.SolicitudInicialAfiliacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gestión de solicitudes iniciales de afiliación
 * Base path: /api/solicitudes-iniciales
 */
@RestController
@RequestMapping("/api/solicitudes-iniciales")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SolicitudInicialAfiliacionController {
    
    @Autowired
    private SolicitudInicialAfiliacionService solicitudInicialService;
    
    @GetMapping
    public ResponseEntity<List<SolicitudInicialAfiliacionResponseDTO>> findAll() {
        return ResponseEntity.ok(solicitudInicialService.findAll());
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SolicitudInicialAfiliacionResponseDTO>> findByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(solicitudInicialService.findByEstado(estado));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudInicialAfiliacionResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(solicitudInicialService.findById(id));
    }
    
    @GetMapping("/token/{token}")
    public ResponseEntity<SolicitudInicialAfiliacionResponseDTO> findByToken(@PathVariable String token) {
        return ResponseEntity.ok(solicitudInicialService.findByToken(token));
    }
    
    @PostMapping
    public ResponseEntity<SolicitudInicialAfiliacionResponseDTO> create(@Valid @RequestBody SolicitudInicialAfiliacionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitudInicialService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudInicialAfiliacionResponseDTO> update(
            @PathVariable Integer id, 
            @Valid @RequestBody SolicitudInicialAfiliacionUpdateDTO dto) {
        return ResponseEntity.ok(solicitudInicialService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        solicitudInicialService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Endpoint para que el admin genere el link de inscripción
     * POST /api/solicitudes-iniciales/{id}/generar-link
     */
    @PostMapping("/{id}/generar-link")
    public ResponseEntity<SolicitudInicialAfiliacionResponseDTO> generarLinkInscripcion(@PathVariable Integer id) {
        return ResponseEntity.ok(solicitudInicialService.generarLinkInscripcion(id));
    }
}

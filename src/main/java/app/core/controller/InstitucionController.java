package app.core.controller;

import app.core.dto.InstitucionDTO;
import app.core.dto.InstitucionResponseDTO;
import app.core.service.InstitucionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instituciones")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class InstitucionController {
    
    @Autowired
    private InstitucionService institucionService;
    
    /**
     * Listar todas las instituciones
     */
    @GetMapping
    public ResponseEntity<List<InstitucionResponseDTO>> findAll() {
        List<InstitucionResponseDTO> instituciones = institucionService.findAll();
        return ResponseEntity.ok(instituciones);
    }
    
    /**
     * Listar instituciones por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<InstitucionResponseDTO>> findByEstado(@PathVariable int estado) {
        List<InstitucionResponseDTO> instituciones = institucionService.findAll(estado);
        return ResponseEntity.ok(instituciones);
    }
    
    /**
     * Obtener institución por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<InstitucionResponseDTO> findById(@PathVariable Integer id) {
        InstitucionResponseDTO institucion = institucionService.findById(id);
        return ResponseEntity.ok(institucion);
    }
    
    /**
     * Crear nueva institución
     */
    @PostMapping
    public ResponseEntity<InstitucionResponseDTO> create(@Valid @RequestBody InstitucionDTO dto) {
        InstitucionResponseDTO institucion = institucionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(institucion);
    }
    
    /**
     * Actualizar institución existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<InstitucionResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody InstitucionDTO dto) {
        InstitucionResponseDTO institucion = institucionService.update(id, dto);
        return ResponseEntity.ok(institucion);
    }
    
    /**
     * Cambiar estado de institución (activar/desactivar)
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<InstitucionResponseDTO> changeStatus(@PathVariable Integer id) {
        InstitucionResponseDTO institucion = institucionService.changeStatus(id);
        return ResponseEntity.ok(institucion);
    }
}

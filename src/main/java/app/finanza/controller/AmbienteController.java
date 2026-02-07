package app.finanza.controller;

import app.finanza.dto.AmbienteCreateDTO;
import app.finanza.dto.AmbienteResponseDTO;
import app.finanza.dto.AmbienteUpdateDTO;
import app.finanza.service.AmbienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gestión de ambientes/salas
 * Base path: /api/ambientes
 */
@RestController
@RequestMapping("/api/ambientes")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AmbienteController {
    
    @Autowired
    private AmbienteService ambienteService;
    
    @GetMapping
    public ResponseEntity<List<AmbienteResponseDTO>> findAll() {
        return ResponseEntity.ok(ambienteService.findAll());
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AmbienteResponseDTO>> findByEstado(@PathVariable Integer estado) {
        return ResponseEntity.ok(ambienteService.findByEstado(estado));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AmbienteResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ambienteService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<AmbienteResponseDTO> create(@Valid @RequestBody AmbienteCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ambienteService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AmbienteResponseDTO> update(
            @PathVariable Integer id, 
            @Valid @RequestBody AmbienteUpdateDTO dto) {
        return ResponseEntity.ok(ambienteService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ambienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

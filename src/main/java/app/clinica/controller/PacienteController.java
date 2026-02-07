package app.clinica.controller;

import app.clinica.dto.PacienteCreateDTO;
import app.clinica.dto.PacienteResponseDTO;
import app.clinica.dto.PacienteUpdateDTO;
import app.clinica.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gestión de pacientes
 * Base path: /api/pacientes
 */
@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;
    
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> findAll() {
        return ResponseEntity.ok(pacienteService.findAll());
    }
    
    @GetMapping("/socio/{socioId}")
    public ResponseEntity<List<PacienteResponseDTO>> findBySocio(@PathVariable Integer socioId) {
        return ResponseEntity.ok(pacienteService.findBySocio(socioId));
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PacienteResponseDTO>> findByEstado(@PathVariable Integer estado) {
        return ResponseEntity.ok(pacienteService.findByEstado(estado));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> create(@Valid @RequestBody PacienteCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> update(
            @PathVariable Integer id, 
            @Valid @RequestBody PacienteUpdateDTO dto) {
        return ResponseEntity.ok(pacienteService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package app.perfil.controller;

import app.perfil.dto.EspecialidadDTO;
import app.perfil.dto.EspecialidadResponseDTO;
import app.perfil.service.EspecialidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class EspecialidadController {
    
    @Autowired
    private EspecialidadService especialidadService;
    
    @GetMapping
    public ResponseEntity<List<EspecialidadResponseDTO>> findAll() {
        return ResponseEntity.ok(especialidadService.findAll());
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EspecialidadResponseDTO>> findByEstado(@PathVariable int estado) {
        return ResponseEntity.ok(especialidadService.findAll(estado));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(especialidadService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<EspecialidadResponseDTO> create(@Valid @RequestBody EspecialidadDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody EspecialidadDTO dto) {
        return ResponseEntity.ok(especialidadService.update(id, dto));
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<EspecialidadResponseDTO> changeStatus(@PathVariable Integer id) {
        return ResponseEntity.ok(especialidadService.changeStatus(id));
    }
}

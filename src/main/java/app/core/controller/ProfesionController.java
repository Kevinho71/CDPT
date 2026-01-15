package app.core.controller;

import app.core.dto.ProfesionDTO;
import app.core.dto.ProfesionResponseDTO;
import app.core.service.ProfesionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesiones")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class ProfesionController {
    
    @Autowired
    private ProfesionService profesionService;
    
    /**
     * Listar todas las profesiones
     */
    @GetMapping
    public ResponseEntity<List<ProfesionResponseDTO>> findAll() {
        List<ProfesionResponseDTO> profesiones = profesionService.findAll();
        return ResponseEntity.ok(profesiones);
    }
    
    /**
     * Listar profesiones por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ProfesionResponseDTO>> findByEstado(@PathVariable int estado) {
        List<ProfesionResponseDTO> profesiones = profesionService.findAll(estado);
        return ResponseEntity.ok(profesiones);
    }
    
    /**
     * Obtener profesión por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfesionResponseDTO> findById(@PathVariable Integer id) {
        ProfesionResponseDTO profesion = profesionService.findById(id);
        return ResponseEntity.ok(profesion);
    }
    
    /**
     * Crear nueva profesión
     */
    @PostMapping
    public ResponseEntity<ProfesionResponseDTO> create(@Valid @RequestBody ProfesionDTO dto) {
        ProfesionResponseDTO profesion = profesionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(profesion);
    }
    
    /**
     * Actualizar profesión existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProfesionResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ProfesionDTO dto) {
        ProfesionResponseDTO profesion = profesionService.update(id, dto);
        return ResponseEntity.ok(profesion);
    }
    
    /**
     * Cambiar estado de profesión (activar/desactivar)
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ProfesionResponseDTO> changeStatus(@PathVariable Integer id) {
        ProfesionResponseDTO profesion = profesionService.changeStatus(id);
        return ResponseEntity.ok(profesion);
    }
}

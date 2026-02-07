package app.cms.controller;

import app.cms.dto.EstadisticasPublicasCreateDTO;
import app.cms.dto.EstadisticasPublicasResponseDTO;
import app.cms.dto.EstadisticasPublicasUpdateDTO;
import app.cms.service.EstadisticasPublicasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gestión de estadísticas públicas del landing page
 * Base path: /api/estadisticas-publicas
 */
@RestController
@RequestMapping("/api/estadisticas-publicas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class EstadisticasPublicasController {
    
    @Autowired
    private EstadisticasPublicasService estadisticasPublicasService;
    
    @GetMapping
    public ResponseEntity<List<EstadisticasPublicasResponseDTO>> findAll() {
        return ResponseEntity.ok(estadisticasPublicasService.findAll());
    }
    
    @GetMapping("/visibles")
    public ResponseEntity<List<EstadisticasPublicasResponseDTO>> findVisible() {
        return ResponseEntity.ok(estadisticasPublicasService.findVisible());
    }
    
    @GetMapping("/visibles/ordenadas")
    public ResponseEntity<List<EstadisticasPublicasResponseDTO>> findVisibleOrdenadas() {
        return ResponseEntity.ok(estadisticasPublicasService.findVisibleOrdenadas());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EstadisticasPublicasResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(estadisticasPublicasService.findById(id));
    }
    
    @GetMapping("/clave/{clave}")
    public ResponseEntity<EstadisticasPublicasResponseDTO> findByClave(@PathVariable String clave) {
        return ResponseEntity.ok(estadisticasPublicasService.findByClave(clave));
    }
    
    @PostMapping
    public ResponseEntity<EstadisticasPublicasResponseDTO> create(@Valid @RequestBody EstadisticasPublicasCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadisticasPublicasService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EstadisticasPublicasResponseDTO> update(
            @PathVariable Integer id, 
            @Valid @RequestBody EstadisticasPublicasUpdateDTO dto) {
        return ResponseEntity.ok(estadisticasPublicasService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        estadisticasPublicasService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

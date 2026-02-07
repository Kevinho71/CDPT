package app.finanza.controller;

import app.finanza.dto.ConfiguracionCuotasCreateDTO;
import app.finanza.dto.ConfiguracionCuotasResponseDTO;
import app.finanza.dto.ConfiguracionCuotasUpdateDTO;
import app.finanza.service.ConfiguracionCuotasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gestión de configuración de cuotas anuales
 * Base path: /api/configuracion-cuotas
 */
@RestController
@RequestMapping("/api/configuracion-cuotas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ConfiguracionCuotasController {
    
    @Autowired
    private ConfiguracionCuotasService configuracionCuotasService;
    
    @GetMapping
    public ResponseEntity<List<ConfiguracionCuotasResponseDTO>> findAll() {
        return ResponseEntity.ok(configuracionCuotasService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ConfiguracionCuotasResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(configuracionCuotasService.findById(id));
    }
    
    @GetMapping("/gestion/{gestion}")
    public ResponseEntity<ConfiguracionCuotasResponseDTO> findByGestion(@PathVariable Integer gestion) {
        return ResponseEntity.ok(configuracionCuotasService.findByGestion(gestion));
    }
    
    @PostMapping
    public ResponseEntity<ConfiguracionCuotasResponseDTO> create(@Valid @RequestBody ConfiguracionCuotasCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(configuracionCuotasService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracionCuotasResponseDTO> update(
            @PathVariable Integer id, 
            @Valid @RequestBody ConfiguracionCuotasUpdateDTO dto) {
        return ResponseEntity.ok(configuracionCuotasService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        configuracionCuotasService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

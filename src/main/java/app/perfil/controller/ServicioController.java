package app.perfil.controller;

import app.perfil.dto.ServicioDTO;
import app.perfil.dto.ServicioResponseDTO;
import app.perfil.service.ServicioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class ServicioController {
    
    @Autowired
    private ServicioService servicioService;
    
    @GetMapping
    public ResponseEntity<List<ServicioResponseDTO>> findAll() {
        return ResponseEntity.ok(servicioService.findAll());
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ServicioResponseDTO>> findByEstado(@PathVariable int estado) {
        return ResponseEntity.ok(servicioService.findAll(estado));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<ServicioResponseDTO> create(@Valid @RequestBody ServicioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody ServicioDTO dto) {
        return ResponseEntity.ok(servicioService.update(id, dto));
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<ServicioResponseDTO> changeStatus(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioService.changeStatus(id));
    }
}

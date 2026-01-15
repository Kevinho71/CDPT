package app.perfil.controller;

import app.perfil.dto.IdiomaDTO;
import app.perfil.dto.IdiomaResponseDTO;
import app.perfil.service.IdiomaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/idiomas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class IdiomaController {
    
    @Autowired
    private IdiomaService idiomaService;
    
    @GetMapping
    public ResponseEntity<List<IdiomaResponseDTO>> findAll() {
        return ResponseEntity.ok(idiomaService.findAll());
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<IdiomaResponseDTO>> findByEstado(@PathVariable int estado) {
        return ResponseEntity.ok(idiomaService.findAll(estado));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<IdiomaResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(idiomaService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<IdiomaResponseDTO> create(@Valid @RequestBody IdiomaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(idiomaService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<IdiomaResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody IdiomaDTO dto) {
        return ResponseEntity.ok(idiomaService.update(id, dto));
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<IdiomaResponseDTO> changeStatus(@PathVariable Integer id) {
        return ResponseEntity.ok(idiomaService.changeStatus(id));
    }
}

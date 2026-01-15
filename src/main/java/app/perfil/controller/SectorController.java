package app.perfil.controller;

import app.perfil.dto.SectorDTO;
import app.perfil.dto.SectorResponseDTO;
import app.perfil.service.SectorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sectores")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class SectorController {
    
    @Autowired
    private SectorService sectorService;
    
    @GetMapping
    public ResponseEntity<List<SectorResponseDTO>> findAll() {
        return ResponseEntity.ok(sectorService.findAll());
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SectorResponseDTO>> findByEstado(@PathVariable int estado) {
        return ResponseEntity.ok(sectorService.findAll(estado));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SectorResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(sectorService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<SectorResponseDTO> create(@Valid @RequestBody SectorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sectorService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SectorResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody SectorDTO dto) {
        return ResponseEntity.ok(sectorService.update(id, dto));
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<SectorResponseDTO> changeStatus(@PathVariable Integer id) {
        return ResponseEntity.ok(sectorService.changeStatus(id));
    }
}

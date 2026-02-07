package app.clinica.controller;

import app.clinica.dto.CitaCreateDTO;
import app.clinica.dto.CitaResponseDTO;
import app.clinica.dto.CitaUpdateDTO;
import app.clinica.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller para gestión de citas
 * Base path: /api/citas
 */
@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CitaController {
    
    @Autowired
    private CitaService citaService;
    
    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> findAll() {
        return ResponseEntity.ok(citaService.findAll());
    }
    
    @GetMapping("/perfil/{perfilSocioId}")
    public ResponseEntity<List<CitaResponseDTO>> findByPerfilSocio(@PathVariable Integer perfilSocioId) {
        return ResponseEntity.ok(citaService.findByPerfilSocio(perfilSocioId));
    }
    
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaResponseDTO>> findByPaciente(@PathVariable Integer pacienteId) {
        return ResponseEntity.ok(citaService.findByPaciente(pacienteId));
    }
    
    @GetMapping("/fecha/{fechaCita}")
    public ResponseEntity<List<CitaResponseDTO>> findByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCita) {
        return ResponseEntity.ok(citaService.findByFecha(fechaCita));
    }
    
    @GetMapping("/estado/{estadoCita}")
    public ResponseEntity<List<CitaResponseDTO>> findByEstado(@PathVariable String estadoCita) {
        return ResponseEntity.ok(citaService.findByEstado(estadoCita));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(citaService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<CitaResponseDTO> create(@Valid @RequestBody CitaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(citaService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> update(
            @PathVariable Integer id, 
            @Valid @RequestBody CitaUpdateDTO dto) {
        return ResponseEntity.ok(citaService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        citaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

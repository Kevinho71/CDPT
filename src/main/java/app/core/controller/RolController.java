package app.core.controller;

import app.core.dto.RolDTO;
import app.core.dto.RolResponseDTO;
import app.core.service.RolServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class RolController {
    
    @Autowired
    private RolServiceImpl rolService;
    
    /**
     * Listar todos los roles
     */
    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> findAll() {
        List<RolResponseDTO> roles = rolService.findAllDTO();
        return ResponseEntity.ok(roles);
    }
    
    /**
     * Listar roles por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RolResponseDTO>> findByEstado(@PathVariable int estado) {
        List<RolResponseDTO> roles = rolService.findAllDTO(estado);
        return ResponseEntity.ok(roles);
    }
    
    /**
     * Obtener rol por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> findById(@PathVariable Long id) {
        RolResponseDTO rol = rolService.findByIdDTO(id);
        return ResponseEntity.ok(rol);
    }
    
    /**
     * Crear nuevo rol
     */
    @PostMapping
    public ResponseEntity<RolResponseDTO> create(@Valid @RequestBody RolDTO dto) {
        RolResponseDTO rol = rolService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(rol);
    }
    
    /**
     * Actualizar rol existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RolDTO dto) {
        RolResponseDTO rol = rolService.updateRol(id, dto);
        return ResponseEntity.ok(rol);
    }
    
    /**
     * Cambiar estado de rol (activar/desactivar)
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<RolResponseDTO> changeStatus(@PathVariable Long id) {
        RolResponseDTO rol = rolService.changeStatusRol(id);
        return ResponseEntity.ok(rol);
    }
}

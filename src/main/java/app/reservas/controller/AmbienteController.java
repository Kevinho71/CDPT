package app.reservas.controller;

import app.reservas.dto.AmbienteDTO;
import app.reservas.dto.ErrorResponseDTO;
import app.reservas.entity.AmbienteEntity;
import app.reservas.repository.AmbienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST independiente para gestión de ambientes (catálogo de espacios físicos).
 * No utiliza ApiResponse genérico - retorna objetos directamente.
 */
@RestController
@RequestMapping("/api/ambientes")
@CrossOrigin(origins = "*")
public class AmbienteController {
    
    @Autowired
    private AmbienteRepository ambienteRepository;
    
    /**
     * Listar todos los ambientes activos.
     * 
     * GET /api/ambientes
     */
    @GetMapping
    public ResponseEntity<?> listarAmbientesActivos() {
        try {
            List<AmbienteEntity> ambientes = ambienteRepository.findByEstado(1);
            
            List<AmbienteDTO> dtos = ambientes.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Internal Server Error", "Error al obtener ambientes: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener un ambiente específico por ID.
     * 
     * GET /api/ambientes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerAmbiente(@PathVariable Integer id) {
        try {
            AmbienteEntity ambiente = ambienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ambiente no encontrado"));
            
            return ResponseEntity.ok(convertirADTO(ambiente));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO("Not Found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Internal Server Error", "Error al obtener ambiente: " + e.getMessage()));
        }
    }
    
    /**
     * Crear nuevo ambiente (solo administradores).
     * 
     * POST /api/ambientes
     */
    @PostMapping
    public ResponseEntity<?> crearAmbiente(@Valid @RequestBody AmbienteDTO dto) {
        try {
            AmbienteEntity ambiente = new AmbienteEntity();
            ambiente.setNombre(dto.getNombre());
            ambiente.setDescripcion(dto.getDescripcion());
            ambiente.setPrecioHora(dto.getPrecioHora());
            ambiente.setEstado(dto.getEstado() != null ? dto.getEstado() : 1);
            
            ambiente = ambienteRepository.save(ambiente);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(ambiente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Internal Server Error", "Error al crear ambiente: " + e.getMessage()));
        }
    }
    
    /**
     * Actualizar ambiente existente (solo administradores).
     * 
     * PUT /api/ambientes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarAmbiente(
            @PathVariable Integer id,
            @Valid @RequestBody AmbienteDTO dto) {
        try {
            AmbienteEntity ambiente = ambienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ambiente no encontrado"));
            
            ambiente.setNombre(dto.getNombre());
            ambiente.setDescripcion(dto.getDescripcion());
            ambiente.setPrecioHora(dto.getPrecioHora());
            if (dto.getEstado() != null) {
                ambiente.setEstado(dto.getEstado());
            }
            
            ambiente = ambienteRepository.save(ambiente);
            
            return ResponseEntity.ok(convertirADTO(ambiente));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO("Not Found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Internal Server Error", "Error al actualizar ambiente: " + e.getMessage()));
        }
    }
    
    /**
     * Desactivar ambiente (solo administradores).
     * 
     * DELETE /api/ambientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactivarAmbiente(@PathVariable Integer id) {
        try {
            AmbienteEntity ambiente = ambienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ambiente no encontrado"));
            
            ambiente.setEstado(0);
            ambienteRepository.save(ambiente);
            
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO("Not Found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Internal Server Error", "Error al desactivar ambiente: " + e.getMessage()));
        }
    }
    
    // MÉTODOS AUXILIARES
    
    private AmbienteDTO convertirADTO(AmbienteEntity entity) {
        return new AmbienteDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getDescripcion(),
            entity.getPrecioHora(),
            entity.getEstado()
        );
    }
}

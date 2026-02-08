package app.reservas.controller;

import app.reservas.dto.*;
import app.reservas.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Controlador REST independiente para gestión de reservas de ambientes.
 * Implementa la lógica de "Una reserva confirmada = Una deuda pendiente".
 * No utiliza ApiResponse genérico - retorna objetos directamente.
 */
@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;
    
    /**
     * Verificar disponibilidad de un ambiente.
     * 
     * GET /api/reservas/disponibilidad?ambienteId=1&fecha=2026-02-15&horaInicio=08:00&horaFin=10:00
     */
    @GetMapping("/disponibilidad")
    public ResponseEntity<?> verificarDisponibilidad(
            @RequestParam Integer ambienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaFin) {
        try {
            DisponibilidadResponseDTO disponibilidad = reservaService.verificarDisponibilidad(
                ambienteId, fecha, horaInicio, horaFin
            );
            
            return ResponseEntity.ok(disponibilidad);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Bad Request", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Internal Server Error", "Error al verificar disponibilidad: " + e.getMessage()));
        }
    }
    
    /**
     * Crear una nueva reserva (genera automáticamente la deuda).
     * 
     * POST /api/reservas
     */
    @PostMapping
    public ResponseEntity<?> crearReserva(@Valid @RequestBody ReservaCreateDTO dto) {
        try {
            ReservaResponseDTO reserva = reservaService.crearReserva(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Bad Request", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Internal Server Error", "Error al crear reserva: " + e.getMessage()));
        }
    }
    
    /**
     * Cancelar una reserva existente.
     * 
     * DELETE /api/reservas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Integer id) {
        try {
            reservaService.cancelarReserva(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Bad Request", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Internal Server Error", "Error al cancelar reserva: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener todas las reservas de un día específico (calendario).
     * 
     * GET /api/reservas/calendario?ambienteId=1&fecha=2026-02-15
     */
    @GetMapping("/calendario")
    public ResponseEntity<?> obtenerReservasDelDia(
            @RequestParam Integer ambienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        try {
            List<ReservaResponseDTO> reservas = reservaService.obtenerReservasDelDia(ambienteId, fecha);
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Internal Server Error", "Error al obtener reservas: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener todas las reservas futuras de un socio.
     * 
     * GET /api/reservas/socio/{socioId}/futuras
     */
    @GetMapping("/socio/{socioId}/futuras")
    public ResponseEntity<?> obtenerReservasFuturas(@PathVariable Integer socioId) {
        try {
            List<ReservaResponseDTO> reservas = reservaService.obtenerReservasFuturasDeSocio(socioId);
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Internal Server Error", "Error al obtener reservas: " + e.getMessage()));
        }
    }
}

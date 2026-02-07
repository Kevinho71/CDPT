package app.finanza.controller;

import app.finanza.dto.TransaccionPagoCreateDTO;
import app.finanza.dto.TransaccionPagoResponseDTO;
import app.finanza.dto.TransaccionPagoUpdateDTO;
import app.finanza.service.TransaccionPagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller para gestión de transacciones de pago
 * Incluye endpoints para el sistema de cuenta corriente con algoritmo FIFO
 * 
 * Base path: /api/transacciones-pago
 */
@RestController
@RequestMapping("/api/transacciones-pago")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TransaccionPagoController {
    
    @Autowired
    private TransaccionPagoService transaccionPagoService;
    
    @GetMapping
    public ResponseEntity<List<TransaccionPagoResponseDTO>> findAll() {
        return ResponseEntity.ok(transaccionPagoService.findAll());
    }
    
    @GetMapping("/socio/{socioId}")
    public ResponseEntity<List<TransaccionPagoResponseDTO>> findBySocio(@PathVariable Integer socioId) {
        return ResponseEntity.ok(transaccionPagoService.findBySocio(socioId));
    }
    
    @GetMapping("/estado/{estadoPago}")
    public ResponseEntity<List<TransaccionPagoResponseDTO>> findByEstado(@PathVariable String estadoPago) {
        return ResponseEntity.ok(transaccionPagoService.findByEstado(estadoPago));
    }
    
    @GetMapping("/rango")
    public ResponseEntity<List<TransaccionPagoResponseDTO>> findByFechaRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(transaccionPagoService.findByFechaRango(fechaInicio, fechaFin));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TransaccionPagoResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(transaccionPagoService.findById(id));
    }
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TransaccionPagoResponseDTO> create(
            @Valid @ModelAttribute TransaccionPagoCreateDTO dto,
            @RequestParam(value = "comprobante", required = false) MultipartFile comprobante) {
        
        TransaccionPagoResponseDTO response = transaccionPagoService.create(dto, comprobante);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TransaccionPagoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @ModelAttribute TransaccionPagoUpdateDTO dto,
            @RequestParam(value = "comprobante", required = false) MultipartFile comprobante) {
        
        return ResponseEntity.ok(transaccionPagoService.update(id, dto, comprobante));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        transaccionPagoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    // ========== NUEVOS ENDPOINTS: SISTEMA DE CUENTA CORRIENTE ==========
    
    /**
     * FLUJO 2: El socio registra un pago (sube comprobante)
     * Crea una transacción en estado EN_REVISION
     * 
     * POST /api/transacciones-pago/socio/{socioId}/registrar
     */
    @PostMapping(value = "/socio/{socioId}/registrar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TransaccionPagoResponseDTO> registrarPagoSocio(
            @PathVariable Integer socioId,
            @RequestParam("montoTotal") String montoTotal,
            @RequestParam(value = "metodoPago", required = false) String metodoPago,
            @RequestParam(value = "referenciaBancaria", required = false) String referenciaBancaria,
            @RequestParam(value = "observaciones", required = false) String observaciones,
            @RequestParam("comprobante") MultipartFile comprobante) {
        
        TransaccionPagoCreateDTO dto = new TransaccionPagoCreateDTO();
        dto.setSocioId(socioId);
        dto.setMontoTotal(new java.math.BigDecimal(montoTotal));
        dto.setMetodoPago(metodoPago);
        dto.setReferenciaBancaria(referenciaBancaria);
        dto.setObservaciones(observaciones);
        
        TransaccionPagoResponseDTO response = transaccionPagoService.registrarPagoSocio(socioId, dto, comprobante);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * FLUJO 3: El admin aprueba un pago y ejecuta el algoritmo FIFO
     * Este es el corazón del sistema: concilia el pago con las deudas
     * 
     * POST /api/transacciones-pago/{transaccionId}/aprobar
     */
    @PostMapping("/{transaccionId}/aprobar")
    public ResponseEntity<TransaccionPagoResponseDTO> aprobarPago(
            @PathVariable Integer transaccionId,
            @RequestParam(value = "adminId", required = false) Integer adminId) {
        
        TransaccionPagoResponseDTO response = transaccionPagoService.aprobarYConciliar(transaccionId, adminId);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Rechazar un pago
     * 
     * POST /api/transacciones-pago/{transaccionId}/rechazar
     */
    @PostMapping("/{transaccionId}/rechazar")
    public ResponseEntity<TransaccionPagoResponseDTO> rechazarPago(
            @PathVariable Integer transaccionId,
            @RequestParam(value = "adminId", required = false) Integer adminId,
            @RequestParam(value = "motivo", required = false) String motivo) {
        
        TransaccionPagoResponseDTO response = transaccionPagoService.rechazarPago(transaccionId, adminId, motivo);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Obtener pagos pendientes de revisión (para dashboard del admin)
     * 
     * GET /api/transacciones-pago/pendientes-revision
     */
    @GetMapping("/pendientes-revision")
    public ResponseEntity<Map<String, Object>> obtenerPendientesRevision() {
        List<TransaccionPagoResponseDTO> pagos = transaccionPagoService.obtenerPagosPendientesRevision();
        
        Map<String, Object> response = new HashMap<>();
        response.put("total", pagos.size());
        response.put("pagos", pagos);
        
        return ResponseEntity.ok(response);
    }
}


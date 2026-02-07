package app.finanza.controller;

import app.finanza.dto.EstadoCuentaSocioDTO;
import app.finanza.service.EstadoCuentaSocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller para gestión de estado de cuenta de socios
 * Endpoints para consultar deudas, verificar morosidad y gestionar obligaciones
 * 
 * Base path: /api/finanzas/estado-cuenta
 */
@RestController
@RequestMapping("/api/finanzas/estado-cuenta")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class EstadoCuentaSocioController {
    
    @Autowired
    private EstadoCuentaSocioService estadoCuentaService;
    
    /**
     * Obtiene el estado de cuenta completo de un socio
     * Incluye TODAS las obligaciones (pendientes, pagadas, vencidas)
     * 
     * GET /api/finanzas/estado-cuenta/socio/{socioId}
     */
    @GetMapping("/socio/{socioId}")
    public ResponseEntity<List<EstadoCuentaSocioDTO>> obtenerEstadoCuenta(@PathVariable Integer socioId) {
        List<EstadoCuentaSocioDTO> estadoCuenta = estadoCuentaService.obtenerEstadoCuenta(socioId);
        return ResponseEntity.ok(estadoCuenta);
    }
    
    /**
     * Obtiene solo las deudas pendientes de un socio
     * Útil para mostrar "lo que debe" en el dashboard
     * 
     * GET /api/finanzas/estado-cuenta/socio/{socioId}/pendientes
     */
    @GetMapping("/socio/{socioId}/pendientes")
    public ResponseEntity<List<EstadoCuentaSocioDTO>> obtenerDeudasPendientes(@PathVariable Integer socioId) {
        List<EstadoCuentaSocioDTO> deudasPendientes = estadoCuentaService.obtenerDeudasPendientes(socioId);
        return ResponseEntity.ok(deudasPendientes);
    }
    
    /**
     * Verifica si un socio está moroso
     * Retorna true si tiene deudas vencidas
     * 
     * GET /api/finanzas/estado-cuenta/socio/{socioId}/morosidad
     */
    @GetMapping("/socio/{socioId}/morosidad")
    public ResponseEntity<Map<String, Object>> verificarMorosidad(@PathVariable Integer socioId) {
        boolean esMoroso = estadoCuentaService.esMoroso(socioId);
        Long deudasVencidas = estadoCuentaService.contarDeudasVencidas(socioId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("socioId", socioId);
        response.put("esMoroso", esMoroso);
        response.put("deudasVencidas", deudasVencidas);
        response.put("mensaje", esMoroso ? 
            "El socio tiene " + deudasVencidas + " deuda(s) vencida(s)" : 
            "El socio está al día con sus pagos");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Crea una obligación manualmente (uso administrativo)
     * Ejemplo: Multa, Cargo especial
     * 
     * POST /api/finanzas/estado-cuenta
     */
    @PostMapping
    public ResponseEntity<EstadoCuentaSocioDTO> crearObligacion(
            @RequestBody EstadoCuentaSocioDTO dto) {
        
        EstadoCuentaSocioDTO created = estadoCuentaService.crearObligacion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    /**
     * Genera la deuda de matrícula para un socio (uso administrativo)
     * Normalmente se ejecuta automáticamente al aprobar la afiliación
     * 
     * POST /api/finanzas/estado-cuenta/socio/{socioId}/matricula
     */
    @PostMapping("/socio/{socioId}/matricula")
    public ResponseEntity<Map<String, String>> generarMatricula(@PathVariable Integer socioId) {
        estadoCuentaService.generarDeudaMatricula(socioId);
        
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Deuda de matrícula generada exitosamente");
        response.put("socioId", socioId.toString());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Trigger manual para generar mensualidades (uso administrativo)
     * Normalmente se ejecuta automáticamente por el cron job
     * 
     * POST /api/finanzas/estado-cuenta/generar-mensualidades
     */
    @PostMapping("/generar-mensualidades")
    public ResponseEntity<Map<String, String>> generarMensualidades() {
        estadoCuentaService.generarMensualidadesAutomaticas();
        
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Mensualidades generadas exitosamente para todos los socios activos");
        
        return ResponseEntity.ok(response);
    }
}

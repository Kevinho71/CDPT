package app.clinica.controller;

import app.clinica.dto.HistoriaClinicaCreateDTO;
import app.clinica.dto.HistoriaClinicaResponseDTO;
import app.clinica.dto.HistoriaClinicaUpdateDTO;
import app.clinica.service.HistoriaClinicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gestión de historias clínicas
 * Base path: /api/historias-clinicas
 */
@RestController
@RequestMapping("/api/historias-clinicas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class HistoriaClinicaController {
    
    @Autowired
    private HistoriaClinicaService historiaClinicaService;
    
    @GetMapping
    public ResponseEntity<List<HistoriaClinicaResponseDTO>> findAll() {
        return ResponseEntity.ok(historiaClinicaService.findAll());
    }
    
    @GetMapping("/paciente/{pacienteId}")
    @Deprecated // Usar /paciente/{pacienteId}/sistemico para historial completo
    public ResponseEntity<List<HistoriaClinicaResponseDTO>> findByPaciente(@PathVariable Integer pacienteId) {
        return ResponseEntity.ok(historiaClinicaService.findByPaciente(pacienteId));
    }
    
    /**
     * GET /api/historias-clinicas/paciente/{pacienteId}/sistemico
     * 
     * 🔥 ENDPOINT SISTÉMICO CRÍTICO 🔥
     * 
     * Obtiene TODO el historial clínico cronológico de un paciente, incluyendo:
     * 1. Notas vinculadas directamente al paciente (sesiones individuales)
     * 2. Notas de citas donde participó (terapias de pareja, familiares, grupales)
     * 
     * CASO DE USO:
     * - Carlos fue a terapia de pareja con Ana
     * - El psicólogo escribió: "La pareja discutió sobre finanzas"
     * - Esta nota está vinculada a la CITA (no a paciente específico)
     * - Al pedir historial de Carlos: APARECE
     * - Al pedir historial de Ana: TAMBIÉN APARECE (es la misma nota compartida)
     * 
     * Luego Carlos fue solo:
     * - El psicólogo escribe: "Carlos confiesa una infidelidad"
     * - Esta nota está vinculada directamente a CARLOS
     * - Al pedir historial de Carlos: APARECE
     * - Al pedir historial de Ana: NO APARECE (privacidad)
     * 
     * RESPONSE: Lista de historias clínicas ordenadas cronológicamente (más recientes primero)
     */
    @GetMapping("/paciente/{pacienteId}/sistemico")
    public ResponseEntity<List<HistoriaClinicaResponseDTO>> findHistorialSistemicoPorPaciente(
            @PathVariable Integer pacienteId) {
        List<HistoriaClinicaResponseDTO> historial = 
            historiaClinicaService.findHistorialSistemicoPorPaciente(pacienteId);
        return ResponseEntity.ok(historial);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HistoriaClinicaResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(historiaClinicaService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<HistoriaClinicaResponseDTO> create(@Valid @RequestBody HistoriaClinicaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(historiaClinicaService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HistoriaClinicaResponseDTO> update(
            @PathVariable Integer id, 
            @Valid @RequestBody HistoriaClinicaUpdateDTO dto) {
        return ResponseEntity.ok(historiaClinicaService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        historiaClinicaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

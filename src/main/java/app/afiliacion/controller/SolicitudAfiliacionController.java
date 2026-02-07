package app.afiliacion.controller;

import app.afiliacion.dto.SolicitudAfiliacionCreateDTO;
import app.afiliacion.dto.SolicitudAfiliacionResponseDTO;
import app.afiliacion.dto.SolicitudAfiliacionUpdateDTO;
import app.afiliacion.service.SolicitudAfiliacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Controller para gestión de solicitudes de afiliación completas
 * Base path: /api/solicitudes-afiliacion
 */
@RestController
@RequestMapping("/api/solicitudes-afiliacion")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SolicitudAfiliacionController {
    
    @Autowired
    private SolicitudAfiliacionService solicitudAfiliacionService;
    
    @GetMapping
    public ResponseEntity<List<SolicitudAfiliacionResponseDTO>> findAll() {
        return ResponseEntity.ok(solicitudAfiliacionService.findAll());
    }
    
    @GetMapping("/estado/{estadoAfiliacion}")
    public ResponseEntity<List<SolicitudAfiliacionResponseDTO>> findByEstado(@PathVariable String estadoAfiliacion) {
        return ResponseEntity.ok(solicitudAfiliacionService.findByEstadoAfiliacion(estadoAfiliacion));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudAfiliacionResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(solicitudAfiliacionService.findById(id));
    }
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SolicitudAfiliacionResponseDTO> create(
            @Valid @ModelAttribute SolicitudAfiliacionCreateDTO dto,
            @RequestParam(value = "fotoCarnetAnverso", required = false) MultipartFile fotoCarnetAnverso,
            @RequestParam(value = "fotoCarnetReverso", required = false) MultipartFile fotoCarnetReverso,
            @RequestParam(value = "fotoTituloProvisicion", required = false) MultipartFile fotoTituloProvisicion,
            @RequestParam(value = "cv", required = false) MultipartFile cv) throws IOException {
        
        SolicitudAfiliacionResponseDTO response = solicitudAfiliacionService.create(
                dto, fotoCarnetAnverso, fotoCarnetReverso, fotoTituloProvisicion, cv);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudAfiliacionResponseDTO> update(
            @PathVariable Integer id, 
            @Valid @RequestBody SolicitudAfiliacionUpdateDTO dto) {
        return ResponseEntity.ok(solicitudAfiliacionService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        solicitudAfiliacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Endpoint público para que el usuario complete su solicitud con token
     * POST /api/solicitudes-afiliacion/con-token?token=xxxxx
     */
    @PostMapping(value = "/con-token", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SolicitudAfiliacionResponseDTO> createConToken(
            @RequestParam("token") String token,
            @Valid @ModelAttribute SolicitudAfiliacionCreateDTO dto,
            @RequestParam(value = "fotoCarnetAnverso", required = false) MultipartFile fotoCarnetAnverso,
            @RequestParam(value = "fotoCarnetReverso", required = false) MultipartFile fotoCarnetReverso,
            @RequestParam(value = "fotoTituloProvisicion", required = false) MultipartFile fotoTituloProvisicion,
            @RequestParam(value = "cv", required = false) MultipartFile cv) throws IOException {
        
        SolicitudAfiliacionResponseDTO response = solicitudAfiliacionService.createConToken(
                token, dto, fotoCarnetAnverso, fotoCarnetReverso, fotoTituloProvisicion, cv);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Endpoint para que el admin apruebe la solicitud
     * POST /api/solicitudes-afiliacion/{id}/aprobar
     */
    @PostMapping("/{id}/aprobar")
    public ResponseEntity<Void> aprobarSolicitud(@PathVariable Integer id) {
        solicitudAfiliacionService.aprobarSolicitud(id);
        return ResponseEntity.ok().build();
    }
}

package app.core.controller;

import app.core.dto.LocationDataResponseDTO;
import app.core.entity.DepartamentoEntity;
import app.core.entity.PaisEntity;
import app.core.entity.ProvinciaEntity;
import app.core.repository.DepartamentoRepository;
import app.core.repository.PaisRepository;
import app.core.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller para datos geográficos (países, departamentos, provincias)
 * Base path: /api/locations
 */
@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    /**
     * Obtiene todos los datos geográficos en una sola respuesta
     * GET /api/locations/all
     * Retorna: países, departamentos y provincias con sus relaciones
     */
    @GetMapping("/all")
    public ResponseEntity<LocationDataResponseDTO> getAllLocationData() {
        // Obtener países activos
        List<PaisEntity> paises = paisRepository.findAll().stream()
            .filter(p -> p.getEstado() == 1)
            .collect(Collectors.toList());
        
        List<LocationDataResponseDTO.PaisDTO> paisesDTOs = paises.stream()
            .map(p -> new LocationDataResponseDTO.PaisDTO(
                p.getId(), 
                p.getNombre(), 
                p.getEstado()
            ))
            .collect(Collectors.toList());
        
        // Obtener departamentos activos
        List<DepartamentoEntity> departamentos = departamentoRepository.findAll().stream()
            .filter(d -> d.getEstado() == 1)
            .collect(Collectors.toList());
        
        List<LocationDataResponseDTO.DepartamentoDTO> departamentosDTOs = departamentos.stream()
            .map(d -> new LocationDataResponseDTO.DepartamentoDTO(
                d.getId(), 
                d.getNombre(), 
                d.getAbreviacion(),
                d.getEstado(),
                d.getPais().getId()
            ))
            .collect(Collectors.toList());
        
        // Obtener provincias activas
        List<ProvinciaEntity> provincias = provinciaRepository.findAll().stream()
            .filter(p -> p.getEstado() == 1)
            .collect(Collectors.toList());
        
        List<LocationDataResponseDTO.ProvinciaDTO> provinciasDTOs = provincias.stream()
            .map(p -> new LocationDataResponseDTO.ProvinciaDTO(
                p.getId(), 
                p.getNombre(), 
                p.getEstado(),
                p.getDepartamento().getId()
            ))
            .collect(Collectors.toList());
        
        LocationDataResponseDTO response = new LocationDataResponseDTO(
            paisesDTOs, 
            departamentosDTOs, 
            provinciasDTOs
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene solo países activos
     * GET /api/locations/countries
     */
    @GetMapping("/countries")
    public ResponseEntity<List<LocationDataResponseDTO.PaisDTO>> getCountries() {
        List<LocationDataResponseDTO.PaisDTO> paises = paisRepository.findAll().stream()
            .filter(p -> p.getEstado() == 1)
            .map(p -> new LocationDataResponseDTO.PaisDTO(
                p.getId(), 
                p.getNombre(), 
                p.getEstado()
            ))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(paises);
    }

    /**
     * Obtiene departamentos por país
     * GET /api/locations/departments?paisId=1
     */
    @GetMapping("/departments")
    public ResponseEntity<List<LocationDataResponseDTO.DepartamentoDTO>> getDepartmentsByCountry(
            @RequestParam(required = false) Integer paisId) {
        
        List<DepartamentoEntity> departamentos = departamentoRepository.findAll().stream()
            .filter(d -> d.getEstado() == 1)
            .filter(d -> paisId == null || d.getPais().getId().equals(paisId))
            .collect(Collectors.toList());
        
        List<LocationDataResponseDTO.DepartamentoDTO> departamentosDTOs = departamentos.stream()
            .map(d -> new LocationDataResponseDTO.DepartamentoDTO(
                d.getId(), 
                d.getNombre(), 
                d.getAbreviacion(),
                d.getEstado(),
                d.getPais().getId()
            ))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(departamentosDTOs);
    }

    /**
     * Obtiene provincias por departamento
     * GET /api/locations/provinces?departamentoId=1
     */
    @GetMapping("/provinces")
    public ResponseEntity<List<LocationDataResponseDTO.ProvinciaDTO>> getProvincesByDepartment(
            @RequestParam(required = false) Integer departamentoId) {
        
        List<ProvinciaEntity> provincias = provinciaRepository.findAll().stream()
            .filter(p -> p.getEstado() == 1)
            .filter(p -> departamentoId == null || p.getDepartamento().getId().equals(departamentoId))
            .collect(Collectors.toList());
        
        List<LocationDataResponseDTO.ProvinciaDTO> provinciasDTOs = provincias.stream()
            .map(p -> new LocationDataResponseDTO.ProvinciaDTO(
                p.getId(), 
                p.getNombre(), 
                p.getEstado(),
                p.getDepartamento().getId()
            ))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(provinciasDTOs);
    }
}

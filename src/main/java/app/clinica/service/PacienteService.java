package app.clinica.service;

import app.clinica.dto.PacienteCreateDTO;
import app.clinica.dto.PacienteResponseDTO;
import app.clinica.dto.PacienteUpdateDTO;

import java.util.List;

public interface PacienteService {
    
    List<PacienteResponseDTO> findAll();
    
    List<PacienteResponseDTO> findBySocio(Integer socioId);
    
    List<PacienteResponseDTO> findByEstado(Integer estado);
    
    PacienteResponseDTO findById(Integer id);
    
    PacienteResponseDTO create(PacienteCreateDTO dto);
    
    PacienteResponseDTO update(Integer id, PacienteUpdateDTO dto);
    
    void delete(Integer id);
}

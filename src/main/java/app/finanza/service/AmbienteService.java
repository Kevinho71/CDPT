package app.finanza.service;

import app.finanza.dto.AmbienteCreateDTO;
import app.finanza.dto.AmbienteResponseDTO;
import app.finanza.dto.AmbienteUpdateDTO;

import java.util.List;

public interface AmbienteService {
    
    List<AmbienteResponseDTO> findAll();
    
    List<AmbienteResponseDTO> findByEstado(Integer estado);
    
    AmbienteResponseDTO findById(Integer id);
    
    AmbienteResponseDTO create(AmbienteCreateDTO dto);
    
    AmbienteResponseDTO update(Integer id, AmbienteUpdateDTO dto);
    
    void delete(Integer id);
}

package app.core.service;

import app.core.dto.ProfesionDTO;
import app.core.dto.ProfesionResponseDTO;
import java.util.List;

public interface ProfesionService {
    
    ProfesionResponseDTO create(ProfesionDTO dto);
    
    ProfesionResponseDTO update(Integer id, ProfesionDTO dto);
    
    ProfesionResponseDTO findById(Integer id);
    
    List<ProfesionResponseDTO> findAll();
    
    List<ProfesionResponseDTO> findAll(int estado);
    
    ProfesionResponseDTO changeStatus(Integer id);
}

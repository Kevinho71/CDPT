package app.core.service;

import app.core.dto.InstitucionDTO;
import app.core.dto.InstitucionResponseDTO;
import java.util.List;

public interface InstitucionService {
    
    InstitucionResponseDTO create(InstitucionDTO dto);
    
    InstitucionResponseDTO update(Integer id, InstitucionDTO dto);
    
    InstitucionResponseDTO findById(Integer id);
    
    List<InstitucionResponseDTO> findAll();
    
    List<InstitucionResponseDTO> findAll(int estado);
    
    InstitucionResponseDTO changeStatus(Integer id);
}

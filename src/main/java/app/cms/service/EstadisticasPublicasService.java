package app.cms.service;

import app.cms.dto.EstadisticasPublicasCreateDTO;
import app.cms.dto.EstadisticasPublicasResponseDTO;
import app.cms.dto.EstadisticasPublicasUpdateDTO;

import java.util.List;

public interface EstadisticasPublicasService {
    
    List<EstadisticasPublicasResponseDTO> findAll();
    
    List<EstadisticasPublicasResponseDTO> findVisible();
    
    List<EstadisticasPublicasResponseDTO> findVisibleOrdenadas();
    
    EstadisticasPublicasResponseDTO findById(Integer id);
    
    EstadisticasPublicasResponseDTO findByClave(String clave);
    
    EstadisticasPublicasResponseDTO create(EstadisticasPublicasCreateDTO dto);
    
    EstadisticasPublicasResponseDTO update(Integer id, EstadisticasPublicasUpdateDTO dto);
    
    void delete(Integer id);
}

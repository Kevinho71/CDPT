package app.finanza.service;

import app.finanza.dto.ConfiguracionCuotasCreateDTO;
import app.finanza.dto.ConfiguracionCuotasResponseDTO;
import app.finanza.dto.ConfiguracionCuotasUpdateDTO;

import java.util.List;

public interface ConfiguracionCuotasService {
    
    List<ConfiguracionCuotasResponseDTO> findAll();
    
    ConfiguracionCuotasResponseDTO findById(Integer id);
    
    ConfiguracionCuotasResponseDTO findByGestion(Integer gestion);
    
    ConfiguracionCuotasResponseDTO create(ConfiguracionCuotasCreateDTO dto);
    
    ConfiguracionCuotasResponseDTO update(Integer id, ConfiguracionCuotasUpdateDTO dto);
    
    void delete(Integer id);
}

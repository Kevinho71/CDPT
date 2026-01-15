package app.perfil.service;

import app.perfil.dto.ServicioDTO;
import app.perfil.dto.ServicioResponseDTO;
import java.util.List;

public interface ServicioService {
    ServicioResponseDTO create(ServicioDTO dto);
    ServicioResponseDTO update(Integer id, ServicioDTO dto);
    ServicioResponseDTO findById(Integer id);
    List<ServicioResponseDTO> findAll();
    List<ServicioResponseDTO> findAll(int estado);
    ServicioResponseDTO changeStatus(Integer id);
}

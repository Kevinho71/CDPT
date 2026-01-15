package app.perfil.service;

import app.perfil.dto.EspecialidadDTO;
import app.perfil.dto.EspecialidadResponseDTO;
import java.util.List;

public interface EspecialidadService {
    EspecialidadResponseDTO create(EspecialidadDTO dto);
    EspecialidadResponseDTO update(Integer id, EspecialidadDTO dto);
    EspecialidadResponseDTO findById(Integer id);
    List<EspecialidadResponseDTO> findAll();
    List<EspecialidadResponseDTO> findAll(int estado);
    EspecialidadResponseDTO changeStatus(Integer id);
}

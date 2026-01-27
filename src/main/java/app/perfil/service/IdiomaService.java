package app.perfil.service;

import app.perfil.dto.IdiomaDTO;
import app.perfil.dto.IdiomaResponseDTO;
import java.util.List;

public interface IdiomaService {
    IdiomaResponseDTO create(IdiomaDTO dto);
    IdiomaResponseDTO update(Integer id, IdiomaDTO dto);
    IdiomaResponseDTO findById(Integer id);
    List<IdiomaResponseDTO> findAll();
    List<IdiomaResponseDTO> findAll(int estado);
    IdiomaResponseDTO changeStatus(Integer id);
}

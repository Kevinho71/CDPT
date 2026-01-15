package app.perfil.service;

import app.perfil.dto.SectorDTO;
import app.perfil.dto.SectorResponseDTO;
import java.util.List;

public interface SectorService {
    SectorResponseDTO create(SectorDTO dto);
    SectorResponseDTO update(Integer id, SectorDTO dto);
    SectorResponseDTO findById(Integer id);
    List<SectorResponseDTO> findAll();
    List<SectorResponseDTO> findAll(int estado);
    SectorResponseDTO changeStatus(Integer id);
}

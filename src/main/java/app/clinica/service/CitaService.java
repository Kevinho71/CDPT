package app.clinica.service;

import app.clinica.dto.CitaCreateDTO;
import app.clinica.dto.CitaResponseDTO;
import app.clinica.dto.CitaUpdateDTO;

import java.time.LocalDate;
import java.util.List;

public interface CitaService {
    
    List<CitaResponseDTO> findAll();
    
    List<CitaResponseDTO> findByPerfilSocio(Integer perfilSocioId);
    
    List<CitaResponseDTO> findByPaciente(Integer pacienteId);
    
    List<CitaResponseDTO> findByFecha(LocalDate fechaCita);
    
    List<CitaResponseDTO> findByEstado(String estadoCita);
    
    CitaResponseDTO findById(Integer id);
    
    CitaResponseDTO create(CitaCreateDTO dto);
    
    CitaResponseDTO update(Integer id, CitaUpdateDTO dto);
    
    void delete(Integer id);
}

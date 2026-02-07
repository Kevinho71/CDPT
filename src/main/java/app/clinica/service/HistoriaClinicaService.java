package app.clinica.service;

import app.clinica.dto.HistoriaClinicaCreateDTO;
import app.clinica.dto.HistoriaClinicaResponseDTO;
import app.clinica.dto.HistoriaClinicaUpdateDTO;

import java.util.List;

public interface HistoriaClinicaService {
    
    List<HistoriaClinicaResponseDTO> findAll();
    
    /**
     * @deprecated Usar findHistorialSistemicoPorPaciente para incluir historias de citas compartidas
     */
    @Deprecated
    List<HistoriaClinicaResponseDTO> findByPaciente(Integer pacienteId);
    
    /**
     * 🔥 MÉTODO SISTÉMICO CRÍTICO 🔥
     * 
     * Obtiene TODO el historial clínico de un paciente, incluyendo:
     * 1. Notas vinculadas directamente al paciente (sesiones individuales, llamadas)
     * 2. Notas de citas donde participó (terapias de pareja, familiares, grupales)
     * 
     * Ejemplo:
     * - Carlos fue a terapia de pareja → Ve esa nota
     * - Carlos fue solo → Ve esa nota privada
     * - Ana (su pareja) SOLO ve la nota compartida, NO la privada de Carlos
     * 
     * @param pacienteId ID del paciente
     * @return Historial clínico completo ordenado cronológicamente
     */
    List<HistoriaClinicaResponseDTO> findHistorialSistemicoPorPaciente(Integer pacienteId);
    
    HistoriaClinicaResponseDTO findById(Integer id);
    
    HistoriaClinicaResponseDTO create(HistoriaClinicaCreateDTO dto);
    
    HistoriaClinicaResponseDTO update(Integer id, HistoriaClinicaUpdateDTO dto);
    
    void delete(Integer id);
}

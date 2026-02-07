package app.clinica.repository;

import app.clinica.entity.HistoriaClinicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinicaEntity, Integer> {
    
    /**
     * Busca historias donde el paciente está vinculado directamente.
     * Útil para notas individuales (llamadas telefónicas, análisis privados).
     */
    List<HistoriaClinicaEntity> findByPaciente_Id(Integer pacienteId);
    
    /**
     * Busca historias de una cita específica.
     * Útil para ver "Qué escribió el psicólogo de la sesión del 20 de febrero".
     */
    List<HistoriaClinicaEntity> findByCita_Id(Integer citaId);
    
    /**
     * 🔥 QUERY SISTÉMICA CRÍTICA 🔥
     * 
     * Obtiene TODO el historial clínico de un paciente, incluyendo:
     * 1. Notas vinculadas directamente al paciente (fk_paciente)
     * 2. Notas de citas donde el paciente participó (a través de cita_participantes)
     * 
     * Ejemplo de uso:
     * - Carlos fue a terapia de pareja con Ana
     * - El psicólogo escribió: "La pareja discutió sobre finanzas"
     * - Esta nota está vinculada a la CITA (no a paciente específico)
     * - Al pedir historial de Carlos: SE MUESTRA
     * - Al pedir historial de Ana: TAMBIÉN SE MUESTRA (es la misma nota)
     * 
     * Luego Carlos va solo:
     * - El psicólogo escribe: "Carlos confiesa una infidelidad"
     * - Esta nota está vinculada directamente a CARLOS
     * - Al pedir historial de Carlos: SE MUESTRA
     * - Al pedir historial de Ana: NO SE MUESTRA (privacidad)
     */
    @Query("""
        SELECT DISTINCT h FROM HistoriaClinicaEntity h
        LEFT JOIN h.cita c
        LEFT JOIN CitaParticipanteEntity cp ON cp.cita.id = c.id
        WHERE h.paciente.id = :pacienteId
           OR cp.paciente.id = :pacienteId
        ORDER BY h.fechaConsulta DESC, h.fechaCreacion DESC
        """)
    List<HistoriaClinicaEntity> findHistorialSistemicoPorPaciente(@Param("pacienteId") Integer pacienteId);
    
    /**
     * Busca historias del psicólogo (todas sus notas).
     * Útil para "Mis últimas 20 notas clínicas".
     */
    @Query("""
        SELECT h FROM HistoriaClinicaEntity h
        WHERE h.cita.perfilSocio.id = :perfilSocioId
        ORDER BY h.fechaConsulta DESC
        """)
    List<HistoriaClinicaEntity> findByPerfilSocio(@Param("perfilSocioId") Integer perfilSocioId);
}

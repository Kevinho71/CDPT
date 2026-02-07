package app.clinica.repository;

import app.clinica.entity.CitaParticipanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaParticipanteRepository extends JpaRepository<CitaParticipanteEntity, Integer> {
    
    /**
     * Busca todos los participantes de una cita específica.
     * Útil para listar "Quiénes asistieron a la sesión del 20 de febrero".
     */
    List<CitaParticipanteEntity> findByCita_Id(Integer citaId);
    
    /**
     * Busca todas las citas en las que un paciente participó.
     * Útil para mostrar "El historial de citas de Carlos".
     */
    List<CitaParticipanteEntity> findByPaciente_Id(Integer pacienteId);
    
    /**
     * Verifica si un paciente ya está registrado en una cita específica.
     * Evita duplicados antes de insertar.
     */
    boolean existsByCita_IdAndPaciente_Id(Integer citaId, Integer pacienteId);
    
    /**
     * Elimina todas las participaciones de una cita.
     * Útil para "resetear" los participantes y volver a asignarlos.
     */
    void deleteByCita_Id(Integer citaId);
}

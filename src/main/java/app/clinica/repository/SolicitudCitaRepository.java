package app.clinica.repository;

import app.clinica.entity.SolicitudCitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudCitaRepository extends JpaRepository<SolicitudCitaEntity, Integer> {
    
    /**
     * Busca todas las solicitudes recibidas por un psicólogo específico.
     * Útil para su "Bandeja de Entrada" de leads.
     */
    List<SolicitudCitaEntity> findByPerfilSocio_IdOrderByFechaSolicitudDesc(Integer perfilSocioId);
    
    /**
     * Filtra solicitudes por estado (PENDIENTE, CONTACTADO, CONVERTIDO, DESCARTADO).
     * Útil para mostrar "Solo los pendientes que no he visto".
     */
    List<SolicitudCitaEntity> findByPerfilSocio_IdAndEstadoOrderByFechaSolicitudDesc(
        Integer perfilSocioId, String estado);
    
    /**
     * Cuenta cuántas solicitudes pendientes tiene un psicólogo.
     * Útil para mostrar badge "Tienes 3 solicitudes nuevas".
     */
    long countByPerfilSocio_IdAndEstado(Integer perfilSocioId, String estado);
}

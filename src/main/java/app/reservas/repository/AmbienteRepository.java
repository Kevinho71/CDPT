package app.reservas.repository;

import app.reservas.entity.AmbienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbienteRepository extends JpaRepository<AmbienteEntity, Integer> {
    
    /**
     * Busca ambientes activos (disponibles para reservar).
     */
    List<AmbienteEntity> findByEstado(Integer estado);
    
    /**
     * Busca por nombre (útil para búsquedas en el frontend).
     */
    List<AmbienteEntity> findByNombreContainingIgnoreCase(String nombre);
}

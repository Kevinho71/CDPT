package app.perfil.repository;

import app.perfil.entity.PerfilSocioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PerfilSocioRepository extends JpaRepository<PerfilSocioEntity, Integer> {
    
    /**
     * Busca un perfil por ID del socio
     */
    Optional<PerfilSocioEntity> findBySocio_Id(Integer socioId);
    
    /**
     * Verifica si ya existe un perfil para un socio
     */
    boolean existsBySocio_Id(Integer socioId);
    
    /**
     * Lista perfiles por estado
     */
    List<PerfilSocioEntity> findByEstado(Integer estado);
    
    /**
     * Lista perfiles públicos
     */
    List<PerfilSocioEntity> findByPerfilPublicoTrueAndEstado(Integer estado);
    
    /**
     * Busca perfiles por ciudad
     */
    List<PerfilSocioEntity> findByCiudadContainingIgnoreCaseAndEstado(String ciudad, Integer estado);
}

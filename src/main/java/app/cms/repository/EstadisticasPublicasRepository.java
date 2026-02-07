package app.cms.repository;

import app.cms.entity.EstadisticasPublicasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadisticasPublicasRepository extends JpaRepository<EstadisticasPublicasEntity, Integer> {
    
    Optional<EstadisticasPublicasEntity> findByClave(String clave);
    
    List<EstadisticasPublicasEntity> findByVisibleTrue();
    
    List<EstadisticasPublicasEntity> findByVisibleTrueOrderByOrdenAsc();
}

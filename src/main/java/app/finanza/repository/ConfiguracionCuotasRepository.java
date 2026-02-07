package app.finanza.repository;

import app.finanza.entity.ConfiguracionCuotasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfiguracionCuotasRepository extends JpaRepository<ConfiguracionCuotasEntity, Integer> {
    
    Optional<ConfiguracionCuotasEntity> findByGestion(Integer gestion);
}

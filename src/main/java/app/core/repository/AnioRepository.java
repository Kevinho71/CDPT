package app.core.repository;

import app.core.entity.AnioEntity;
import app.common.util.GenericRepositoryNormal;
import org.springframework.stereotype.Repository;

@Repository
public interface AnioRepository extends GenericRepositoryNormal<AnioEntity, Long> {
    
    // Spring Data JPA will automatically generate the query based on method name
    // Searches by the 'nombre' field in AnioEntity
    AnioEntity findByNombre(String nombre);
}

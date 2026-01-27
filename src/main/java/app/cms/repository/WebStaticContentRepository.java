package app.cms.repository;

import app.cms.entity.WebStaticContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para gestión de contenidos estáticos
 */
@Repository
public interface WebStaticContentRepository extends JpaRepository<WebStaticContentEntity, String> {

    /**
     * Busca un contenido por su clave única
     */
    Optional<WebStaticContentEntity> findByClave(String clave);

    /**
     * Verifica si existe una clave
     */
    boolean existsByClave(String clave);
}

package app.auth.repository;

import app.auth.entity.UsuarioSocialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioSocialRepository extends JpaRepository<UsuarioSocialEntity, Integer> {
    
    /**
     * Busca usuario social por provider y provider_id (paso 1 del flujo OAuth2)
     */
    Optional<UsuarioSocialEntity> findByProviderAndProviderId(String provider, String providerId);
    
    /**
     * Busca usuario social por email y provider
     */
    Optional<UsuarioSocialEntity> findByEmailAndProvider(String email, String provider);
    
    /**
     * Verifica si existe un provider_id para un proveedor específico
     */
    boolean existsByProviderAndProviderId(String provider, String providerId);
}

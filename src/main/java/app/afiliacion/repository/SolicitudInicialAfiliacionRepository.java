package app.afiliacion.repository;

import app.afiliacion.entity.SolicitudInicialAfiliacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitudInicialAfiliacionRepository extends JpaRepository<SolicitudInicialAfiliacionEntity, Integer> {
    
    List<SolicitudInicialAfiliacionEntity> findByEstado(String estado);
    
    Optional<SolicitudInicialAfiliacionEntity> findByTokenAcceso(String tokenAcceso);
    
    Optional<SolicitudInicialAfiliacionEntity> findByCorreo(String correo);
}

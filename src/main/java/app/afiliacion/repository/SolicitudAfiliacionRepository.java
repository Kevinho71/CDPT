package app.afiliacion.repository;

import app.afiliacion.entity.SolicitudAfiliacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudAfiliacionRepository extends JpaRepository<SolicitudAfiliacionEntity, Integer> {
    
    List<SolicitudAfiliacionEntity> findByEstadoAfiliacion(String estadoAfiliacion);
    
    List<SolicitudAfiliacionEntity> findByFkSolicitudInicialAfiliacion_Id(Integer solicitudInicialId);
}

package app.finanza.repository;

import app.finanza.entity.AmbienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbienteRepository extends JpaRepository<AmbienteEntity, Integer> {
    
    List<AmbienteEntity> findByEstado(Integer estado);
}

package app.clinica.repository;

import app.clinica.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteEntity, Integer> {
    
    List<PacienteEntity> findByFkSocio_Id(Integer socioId);
    
    List<PacienteEntity> findByEstado(Integer estado);
}

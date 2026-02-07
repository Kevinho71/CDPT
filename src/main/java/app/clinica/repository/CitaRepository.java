package app.clinica.repository;

import app.clinica.entity.CitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<CitaEntity, Integer> {
    
    List<CitaEntity> findByFkPerfilSocio_Id(Integer perfilSocioId);
    
    List<CitaEntity> findByFkPaciente_Id(Integer pacienteId);
    
    List<CitaEntity> findByFechaCita(LocalDate fechaCita);
    
    List<CitaEntity> findByEstadoCita(String estadoCita);
}

package app.finanza.repository;

import app.finanza.entity.TransaccionPagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransaccionPagoRepository extends JpaRepository<TransaccionPagoEntity, Integer> {
    
    List<TransaccionPagoEntity> findBySocio_Id(Integer socioId);
    
    List<TransaccionPagoEntity> findByEstado(String estado);
    
    List<TransaccionPagoEntity> findByFechaPagoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}

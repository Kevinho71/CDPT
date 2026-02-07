package app.finanza.repository;

import app.finanza.entity.TransaccionPagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransaccionPagoRepository extends JpaRepository<TransaccionPagoEntity, Integer> {
    
    List<TransaccionPagoEntity> findByFkSocio_Id(Integer socioId);
    
    List<TransaccionPagoEntity> findByEstadoPago(String estadoPago);
    
    List<TransaccionPagoEntity> findByFechaTransaccionBetween(LocalDate fechaInicio, LocalDate fechaFin);
}

package app.finanza.repository;

import app.finanza.entity.DetallePagoDeudaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePagoDeudaRepository extends JpaRepository<DetallePagoDeudaEntity, Integer> {
    
    List<DetallePagoDeudaEntity> findByTransaccionId(Integer transaccionId);
    
    List<DetallePagoDeudaEntity> findByEstadoCuentaId(Integer estadoCuentaId);
}

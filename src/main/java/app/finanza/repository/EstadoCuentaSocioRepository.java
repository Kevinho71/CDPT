package app.finanza.repository;

import app.finanza.entity.EstadoCuentaSocioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EstadoCuentaSocioRepository extends JpaRepository<EstadoCuentaSocioEntity, Integer> {
    
    List<EstadoCuentaSocioEntity> findBySocioId(Integer socioId);
    
    List<EstadoCuentaSocioEntity> findBySocioIdAndEstadoPago(Integer socioId, String estadoPago);
    
    List<EstadoCuentaSocioEntity> findByGestionAndTipoObligacion(Integer gestion, String tipoObligacion);
    
    /**
     * Obtiene deudas pendientes o parciales de un socio ordenadas por fecha de vencimiento (FIFO)
     * CRÍTICO para el algoritmo de conciliación
     */
    @Query("SELECT e FROM EstadoCuentaSocioEntity e WHERE e.socio.id = :socioId " +
           "AND e.estadoPago IN ('PENDIENTE', 'PARCIAL') " +
           "ORDER BY e.fechaVencimiento ASC, e.id ASC")
    List<EstadoCuentaSocioEntity> findDeudasPendientesFIFO(@Param("socioId") Integer socioId);
    
    /**
     * Consulta de morosidad: cuenta cuántas deudas vencidas tiene un socio
     */
    @Query("SELECT COUNT(e) FROM EstadoCuentaSocioEntity e WHERE e.socio.id = :socioId " +
           "AND e.estadoPago != 'PAGADO' " +
           "AND e.fechaVencimiento < :fechaActual")
    Long contarDeudasVencidas(@Param("socioId") Integer socioId, @Param("fechaActual") LocalDate fechaActual);
    
    /**
     * Verifica si existe una obligación para un socio en un mes/gestión específicos
     */
    @Query("SELECT COUNT(e) > 0 FROM EstadoCuentaSocioEntity e WHERE e.socio.id = :socioId " +
           "AND e.gestion = :gestion AND e.mes = :mes AND e.tipoObligacion = :tipo")
    boolean existeObligacion(@Param("socioId") Integer socioId, 
                            @Param("gestion") Integer gestion,
                            @Param("mes") Integer mes, 
                            @Param("tipo") String tipo);
    
    /**
     * Obtiene el estado de cuenta del socio (últimos movimientos)
     */
    @Query("SELECT e FROM EstadoCuentaSocioEntity e WHERE e.socio.id = :socioId " +
           "ORDER BY e.fechaEmision DESC, e.id DESC")
    List<EstadoCuentaSocioEntity> findEstadoCuentaOrdenado(@Param("socioId") Integer socioId);
    
    /**
     * Busca la deuda generada por una reserva específica.
     * CRÍTICO para el módulo de Reservas: Permite anular la deuda si se cancela la reserva.
     */
    @Query("SELECT e FROM EstadoCuentaSocioEntity e WHERE e.reservaId = :reservaId")
    EstadoCuentaSocioEntity findByReservaId(@Param("reservaId") Integer reservaId);
    
    /**
     * Cuenta deudas vencidas hace más de X meses (para bloquear reservas).
     * 
     * @param socioId ID del socio
     * @param fechaLimite Fecha límite (ej: hace 3 meses)
     * @return Cantidad de deudas vencidas antes de esa fecha
     */
    @Query("SELECT COUNT(e) FROM EstadoCuentaSocioEntity e WHERE e.socio.id = :socioId " +
           "AND e.estadoPago IN ('PENDIENTE', 'PARCIAL') " +
           "AND e.fechaVencimiento < :fechaLimite")
    Long contarDeudasVencidasAntesde(@Param("socioId") Integer socioId, 
                                     @Param("fechaLimite") LocalDate fechaLimite);
}

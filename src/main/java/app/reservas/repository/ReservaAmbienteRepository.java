package app.reservas.repository;

import app.reservas.entity.ReservaAmbienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaAmbienteRepository extends JpaRepository<ReservaAmbienteEntity, Integer> {
    
    /**
     * 🔥 QUERY CRÍTICA: OVERLAP CHECK (Validación de Disponibilidad)
     * 
     * Verifica si existe una reserva confirmada que se solape con el horario solicitado.
     * 
     * Lógica de Solapamiento:
     * Dos reservas SE SOLAPAN si:
     * - La hora de inicio de una está ANTES de la hora de fin de la otra Y
     * - La hora de fin de una está DESPUÉS de la hora de inicio de la otra
     * 
     * Ejemplo Visual:
     * Reserva Existente:  14:00 ████████ 16:00
     * Solicitud:                 15:00 ████ 17:00  ❌ SE SOLAPA
     * Solicitud:          13:00 ██ 14:00            ✅ NO SE SOLAPA
     * Solicitud:                             16:00 ██ 17:00  ✅ NO SE SOLAPA
     * 
     * @param ambienteId ID del ambiente a verificar
     * @param fecha Fecha de la reserva
     * @param horaInicio Hora de inicio solicitada
     * @param horaFin Hora de fin solicitada
     * @return true si existe solapamiento (NO DISPONIBLE), false si está libre
     */
    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
        FROM ReservaAmbienteEntity r
        WHERE r.ambiente.id = :ambienteId
          AND r.fechaReserva = :fecha
          AND r.estadoReserva = 'CONFIRMADA'
          AND (r.horaInicio < :horaFin AND r.horaFin > :horaInicio)
        """)
    boolean existeSolapamiento(@Param("ambienteId") Integer ambienteId,
                               @Param("fecha") LocalDate fecha,
                               @Param("horaInicio") LocalTime horaInicio,
                               @Param("horaFin") LocalTime horaFin);
    
    /**
     * Obtiene todas las reservas de un ambiente en una fecha específica.
     * Útil para mostrar calendario de ocupación.
     */
    @Query("""
        SELECT r FROM ReservaAmbienteEntity r
        WHERE r.ambiente.id = :ambienteId
          AND r.fechaReserva = :fecha
          AND r.estadoReserva = 'CONFIRMADA'
        ORDER BY r.horaInicio ASC
        """)
    List<ReservaAmbienteEntity> findReservasDelDia(@Param("ambienteId") Integer ambienteId,
                                                    @Param("fecha") LocalDate fecha);
    
    /**
     * Busca reservas de un socio específico.
     * Útil para el historial personal "Mis Reservas".
     */
    List<ReservaAmbienteEntity> findBySocio_IdOrderByFechaReservaDesc(Integer socioId);
    
    /**
     * Filtra reservas por estado de pago.
     * Útil para reportes de caja: "Reservas pendientes de pago".
     */
    List<ReservaAmbienteEntity> findByEstadoPagoOrderByFechaReservaAsc(String estadoPago);
    
    /**
     * Busca reserva por estado de reserva.
     */
    List<ReservaAmbienteEntity> findByEstadoReservaOrderByFechaReservaDesc(String estadoReserva);
    
    /**
     * Busca reservas futuras de un socio.
     * Útil para validar si tiene reservas pendientes antes de aplicar sanciones.
     */
    @Query("""
        SELECT r FROM ReservaAmbienteEntity r
        WHERE r.socio.id = :socioId
          AND r.fechaReserva >= :fechaActual
          AND r.estadoReserva = 'CONFIRMADA'
        ORDER BY r.fechaReserva ASC, r.horaInicio ASC
        """)
    List<ReservaAmbienteEntity> findReservasFuturas(@Param("socioId") Integer socioId,
                                                     @Param("fechaActual") LocalDate fechaActual);
}

package app.reservas.service;

import app.reservas.dto.*;
import app.reservas.entity.ReservaAmbienteEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface del servicio de reservas de ambientes.
 */
public interface ReservaService {
    
    /**
     * Crea una reserva y genera automáticamente la deuda en estado_cuenta_socio.
     * Operación ATÓMICA: Si falla la creación de la deuda, se hace rollback de la reserva.
     * 
     * Validaciones:
     * - Verifica que el ambiente existe y está activo
     * - Verifica que el socio existe
     * - Verifica que no hay solapamiento con otras reservas
     * - Verifica que el socio no tiene morosidad (deudas > 3 meses)
     * - Calcula monto = precio_hora * (hora_fin - hora_inicio)
     * 
     * @param dto Datos de la reserva
     * @return Reserva creada con su ID y deuda vinculada
     * @throws IllegalArgumentException Si hay validaciones fallidas
     */
    ReservaResponseDTO crearReserva(ReservaCreateDTO dto);
    
    /**
     * Cancela una reserva y elimina/anula la deuda vinculada si está PENDIENTE.
     * Si la deuda ya está PAGADA, se puede generar un saldo a favor o simplemente no reembolsar
     * según la política del colegio.
     * 
     * @param reservaId ID de la reserva
     * @throws IllegalArgumentException Si la reserva no existe
     */
    void cancelarReserva(Integer reservaId);
    
    /**
     * Verifica disponibilidad de un ambiente en un rango de tiempo específico.
     * 
     * @param ambienteId ID del ambiente
     * @param fecha Fecha de la reserva
     * @param horaInicio Hora de inicio
     * @param horaFin Hora de fin
     * @return DTO con disponibilidad y mensaje explicativo
     */
    DisponibilidadResponseDTO verificarDisponibilidad(Integer ambienteId, LocalDate fecha, 
                                                      java.time.LocalTime horaInicio, java.time.LocalTime horaFin);
    
    /**
     * Obtiene todas las reservas de un día específico para un ambiente (calendario).
     * 
     * @param ambienteId ID del ambiente
     * @param fecha Fecha a consultar
     * @return Lista de reservas del día
     */
    List<ReservaResponseDTO> obtenerReservasDelDia(Integer ambienteId, LocalDate fecha);
    
    /**
     * Obtiene todas las reservas futuras de un socio.
     * 
     * @param socioId ID del socio
     * @return Lista de reservas futuras
     */
    List<ReservaResponseDTO> obtenerReservasFuturasDeSocio(Integer socioId);
    
    /**
     * Actualiza el estado de pago de una reserva cuando la deuda es pagada por el sistema FIFO.
     * Este método es llamado por TransaccionPagoService después de aplicar pagos.
     * 
     * @param reservaId ID de la reserva
     */
    void marcarReservaComoPagada(Integer reservaId);
}

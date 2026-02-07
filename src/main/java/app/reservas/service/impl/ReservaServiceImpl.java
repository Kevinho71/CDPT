package app.reservas.service.impl;

import app.finanza.entity.EstadoCuentaSocioEntity;
import app.finanza.repository.EstadoCuentaSocioRepository;
import app.reservas.dto.*;
import app.reservas.entity.AmbienteEntity;
import app.reservas.entity.ReservaAmbienteEntity;
import app.reservas.repository.AmbienteRepository;
import app.reservas.repository.ReservaAmbienteRepository;
import app.reservas.service.ReservaService;
import app.socio.entity.SocioEntity;
import app.socio.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de reservas con lógica transaccional.
 * CRÍTICO: Las operaciones de creación/cancelación son ATÓMICAS con la contabilidad.
 */
@Service
public class ReservaServiceImpl implements ReservaService {
    
    @Autowired
    private ReservaAmbienteRepository reservaRepository;
    
    @Autowired
    private AmbienteRepository ambienteRepository;
    
    @Autowired
    private SocioRepository socioRepository;
    
    @Autowired
    private EstadoCuentaSocioRepository estadoCuentaRepository;
    
    @Override
    @Transactional
    public ReservaResponseDTO crearReserva(ReservaCreateDTO dto) {
        // VALIDACIÓN 1: Ambiente existe y está activo
        AmbienteEntity ambiente = ambienteRepository.findById(dto.getAmbienteId())
            .orElseThrow(() -> new IllegalArgumentException("Ambiente no encontrado"));
        
        if (ambiente.getEstado() == null || ambiente.getEstado() != 1) {
            throw new IllegalArgumentException("El ambiente no está disponible (estado inactivo)");
        }
        
        // VALIDACIÓN 2: Socio existe
        SocioEntity socio = socioRepository.findById(dto.getSocioId())
            .orElseThrow(() -> new IllegalArgumentException("Socio no encontrado"));
        
        // VALIDACIÓN 3: Horario lógico
        if (dto.getHoraFin().isBefore(dto.getHoraInicio()) || dto.getHoraFin().equals(dto.getHoraInicio())) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio");
        }
        
        // VALIDACIÓN 4: Solapamiento con otras reservas
        boolean haySolapamiento = reservaRepository.existeSolapamiento(
            dto.getAmbienteId(),
            dto.getFechaReserva(),
            dto.getHoraInicio(),
            dto.getHoraFin()
        );
        
        if (haySolapamiento) {
            throw new IllegalArgumentException("El horario solicitado ya está reservado");
        }
        
        // VALIDACIÓN 5: Política "Cero Morosidad" - Bloqueo si existe CUALQUIER deuda vencida
        Long deudasVencidas = estadoCuentaRepository.contarDeudasVencidas(dto.getSocioId(), LocalDate.now());
        
        if (deudasVencidas != null && deudasVencidas > 0) {
            throw new IllegalArgumentException(
                "No puede realizar reservas mientras tenga deudas pendientes. " +
                "Por favor, regularice sus obligaciones antes de reservar. Deudas vencidas: " + deudasVencidas
            );
        }
        
        // CÁLCULO DEL MONTO
        long minutosReserva = ChronoUnit.MINUTES.between(dto.getHoraInicio(), dto.getHoraFin());
        BigDecimal horasReserva = new BigDecimal(minutosReserva).divide(new BigDecimal(60), 2, RoundingMode.HALF_UP);
        BigDecimal montoTotal = ambiente.getPrecioHora().multiply(horasReserva).setScale(2, RoundingMode.HALF_UP);
        
        // CREAR RESERVA
        ReservaAmbienteEntity reserva = new ReservaAmbienteEntity();
        reserva.setAmbiente(ambiente);
        reserva.setSocio(socio);
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setHoraInicio(dto.getHoraInicio());
        reserva.setHoraFin(dto.getHoraFin());
        reserva.setMontoTotal(montoTotal);
        reserva.setEstadoReserva("CONFIRMADA");
        reserva.setEstadoPago("PENDIENTE");
        reserva.setFechaCreacion(LocalDateTime.now());
        
        reserva = reservaRepository.save(reserva);
        
        // CREAR DEUDA EN ESTADO_CUENTA_SOCIO (CONTABILIDAD UNIFICADA)
        EstadoCuentaSocioEntity deuda = new EstadoCuentaSocioEntity();
        deuda.setSocio(socio);
        deuda.setTipoObligacion("ALQUILER");
        deuda.setConcepto(generarConcepto(ambiente, dto.getFechaReserva()));
        deuda.setReservaId(reserva.getId()); // Bidirectional link
        deuda.setFechaEmision(LocalDate.now());
        
        // Fecha de vencimiento: 15 días después de la fecha de reserva
        deuda.setFechaVencimiento(dto.getFechaReserva().plusDays(15));
        
        deuda.setMontoOriginal(montoTotal);
        deuda.setMontoPagadoAcumulado(BigDecimal.ZERO); // Saldo = monto_original - monto_pagado
        deuda.setEstadoPago("PENDIENTE");
        deuda.setGestion(dto.getFechaReserva().getYear());
        deuda.setMes(dto.getFechaReserva().getMonthValue());
        
        deuda = estadoCuentaRepository.save(deuda);
        
        // CONSTRUIR RESPUESTA
        return construirResponse(reserva, deuda.getId());
    }
    
    @Override
    @Transactional
    public void cancelarReserva(Integer reservaId) {
        ReservaAmbienteEntity reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        
        if ("CANCELADA".equals(reserva.getEstadoReserva())) {
            throw new IllegalArgumentException("La reserva ya está cancelada");
        }
        
        // Cambiar estado de la reserva
        reserva.setEstadoReserva("CANCELADA");
        reservaRepository.save(reserva);
        
        // Buscar la deuda vinculada
        EstadoCuentaSocioEntity deuda = estadoCuentaRepository.findByReservaId(reservaId);
        
        if (deuda != null) {
            if ("PENDIENTE".equals(deuda.getEstadoPago())) {
                // Si la deuda no ha sido pagada, la eliminamos
                estadoCuentaRepository.delete(deuda);
            } else if ("PAGADO".equals(deuda.getEstadoPago())) {
                // Si ya fue pagada, se puede implementar lógica de reembolso o saldo a favor
                // Por ahora: solo actualizamos el concepto para indicar que fue cancelada
                deuda.setConcepto(deuda.getConcepto() + " [CANCELADA - SIN REEMBOLSO]");
                estadoCuentaRepository.save(deuda);
            }
        }
    }
    
    @Override
    public DisponibilidadResponseDTO verificarDisponibilidad(Integer ambienteId, LocalDate fecha,
                                                             LocalTime horaInicio, LocalTime horaFin) {
        // Verificar que el ambiente existe y está activo
        AmbienteEntity ambiente = ambienteRepository.findById(ambienteId)
            .orElseThrow(() -> new IllegalArgumentException("Ambiente no encontrado"));
        
        if (ambiente.getEstado() == null || ambiente.getEstado() != 1) {
            return new DisponibilidadResponseDTO(false, "El ambiente no está disponible (estado inactivo)");
        }
        
        // Verificar horario lógico
        if (horaFin.isBefore(horaInicio) || horaFin.equals(horaInicio)) {
            return new DisponibilidadResponseDTO(false, "La hora de fin debe ser posterior a la hora de inicio");
        }
        
        // Verificar solapamiento
        boolean haySolapamiento = reservaRepository.existeSolapamiento(ambienteId, fecha, horaInicio, horaFin);
        
        if (haySolapamiento) {
            return new DisponibilidadResponseDTO(false, "El horario está ocupado");
        }
        
        return new DisponibilidadResponseDTO(true, "El horario está disponible");
    }
    
    @Override
    public List<ReservaResponseDTO> obtenerReservasDelDia(Integer ambienteId, LocalDate fecha) {
        List<ReservaAmbienteEntity> reservas = reservaRepository.findReservasDelDia(ambienteId, fecha);
        
        return reservas.stream()
            .map(r -> construirResponse(r, obtenerDeudaId(r.getId())))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<ReservaResponseDTO> obtenerReservasFuturasDeSocio(Integer socioId) {
        List<ReservaAmbienteEntity> reservas = reservaRepository.findReservasFuturas(socioId, LocalDate.now());
        
        return reservas.stream()
            .map(r -> construirResponse(r, obtenerDeudaId(r.getId())))
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void marcarReservaComoPagada(Integer reservaId) {
        ReservaAmbienteEntity reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        
        reserva.setEstadoPago("PAGADO");
        reservaRepository.save(reserva);
    }
    
    // MÉTODOS AUXILIARES
    
    /**
     * Genera un concepto descriptivo para la deuda.
     * Ejemplo: "Alquiler Consultorio 1 - Evento Privado (15/Feb/2026)"
     */
    private String generarConcepto(AmbienteEntity ambiente, LocalDate fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
        return String.format("Alquiler %s (%s)", ambiente.getNombre(), fecha.format(formatter));
    }
    
    /**
     * Construye el DTO de respuesta con todos los datos de la reserva.
     */
    private ReservaResponseDTO construirResponse(ReservaAmbienteEntity reserva, Integer deudaId) {
        String socioNombre = reserva.getSocio().getNombre() + " " + reserva.getSocio().getApellidoPaterno();
        
        return new ReservaResponseDTO(
            reserva.getId(),
            reserva.getAmbiente().getId(),
            reserva.getAmbiente().getNombre(),
            reserva.getSocio().getId(),
            socioNombre,
            reserva.getFechaReserva(),
            reserva.getHoraInicio(),
            reserva.getHoraFin(),
            reserva.getMontoTotal(),
            reserva.getEstadoReserva(),
            reserva.getEstadoPago(),
            reserva.getFechaCreacion(),
            deudaId
        );
    }
    
    /**
     * Obtiene el ID de la deuda vinculada a una reserva.
     */
    private Integer obtenerDeudaId(Integer reservaId) {
        EstadoCuentaSocioEntity deuda = estadoCuentaRepository.findByReservaId(reservaId);
        return deuda != null ? deuda.getId() : null;
    }
}

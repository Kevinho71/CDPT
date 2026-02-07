package app.reservas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO de respuesta con toda la información de una reserva.
 */
public class ReservaResponseDTO {
    
    private Integer id;
    private Integer ambienteId;
    private String ambienteNombre;
    private Integer socioId;
    private String socioNombre;
    private LocalDate fechaReserva;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private BigDecimal montoTotal;
    private String estadoReserva;
    private String estadoPago;
    private LocalDateTime fechaCreacion;
    
    // Para mostrar si existe deuda vinculada
    private Integer deudaId;
    
    // Constructores
    public ReservaResponseDTO() {}
    
    public ReservaResponseDTO(Integer id, Integer ambienteId, String ambienteNombre,
                             Integer socioId, String socioNombre, LocalDate fechaReserva,
                             LocalTime horaInicio, LocalTime horaFin, BigDecimal montoTotal,
                             String estadoReserva, String estadoPago, LocalDateTime fechaCreacion,
                             Integer deudaId) {
        this.id = id;
        this.ambienteId = ambienteId;
        this.ambienteNombre = ambienteNombre;
        this.socioId = socioId;
        this.socioNombre = socioNombre;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.montoTotal = montoTotal;
        this.estadoReserva = estadoReserva;
        this.estadoPago = estadoPago;
        this.fechaCreacion = fechaCreacion;
        this.deudaId = deudaId;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getAmbienteId() {
        return ambienteId;
    }
    
    public void setAmbienteId(Integer ambienteId) {
        this.ambienteId = ambienteId;
    }
    
    public String getAmbienteNombre() {
        return ambienteNombre;
    }
    
    public void setAmbienteNombre(String ambienteNombre) {
        this.ambienteNombre = ambienteNombre;
    }
    
    public Integer getSocioId() {
        return socioId;
    }
    
    public void setSocioId(Integer socioId) {
        this.socioId = socioId;
    }
    
    public String getSocioNombre() {
        return socioNombre;
    }
    
    public void setSocioNombre(String socioNombre) {
        this.socioNombre = socioNombre;
    }
    
    public LocalDate getFechaReserva() {
        return fechaReserva;
    }
    
    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
    
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    public LocalTime getHoraFin() {
        return horaFin;
    }
    
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
    
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }
    
    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }
    
    public String getEstadoReserva() {
        return estadoReserva;
    }
    
    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
    
    public String getEstadoPago() {
        return estadoPago;
    }
    
    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Integer getDeudaId() {
        return deudaId;
    }
    
    public void setDeudaId(Integer deudaId) {
        this.deudaId = deudaId;
    }
}

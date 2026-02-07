package app.reservas.entity;

import app.socio.entity.SocioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Entidad que representa una reserva de ambiente por parte de un socio.
 * 
 * REGLA DE ORO: Una reserva confirmada = Una deuda pendiente en estado_cuenta_socio.
 * 
 * Estados:
 * - estado_reserva: CONFIRMADA, CANCELADA
 * - estado_pago: PENDIENTE, PAGADO
 */
@Entity
@Table(name = "reserva_ambiente")
public class ReservaAmbienteEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_ambiente", nullable = false)
    private AmbienteEntity ambiente;
    
    @ManyToOne
    @JoinColumn(name = "fk_socio", nullable = false)
    private SocioEntity socio;
    
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;
    
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;
    
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;
    
    /**
     * Monto total calculado: precio_hora * (hora_fin - hora_inicio).
     * Este valor se replica en estado_cuenta_socio.monto_original.
     */
    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;
    
    /**
     * Estado de la reserva:
     * - CONFIRMADA: La reserva está activa
     * - CANCELADA: El socio canceló la reserva
     */
    @Column(name = "estado_reserva", length = 20)
    private String estadoReserva = "CONFIRMADA";
    
    /**
     * Estado del pago:
     * - PENDIENTE: El socio aún no ha pagado esta obligación
     * - PAGADO: El pago fue registrado y conciliado por el algoritmo FIFO
     * 
     * IMPORTANTE: Este campo se actualiza automáticamente cuando el
     * registro vinculado en estado_cuenta_socio cambie a PAGADO.
     */
    @Column(name = "estado_pago", length = 20)
    private String estadoPago = "PENDIENTE";
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    // Constructores
    public ReservaAmbienteEntity() {}
    
    public ReservaAmbienteEntity(Integer id, AmbienteEntity ambiente, SocioEntity socio,
                                LocalDate fechaReserva, LocalTime horaInicio, LocalTime horaFin,
                                BigDecimal montoTotal, String estadoReserva, String estadoPago,
                                LocalDateTime fechaCreacion) {
        this.id = id;
        this.ambiente = ambiente;
        this.socio = socio;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.montoTotal = montoTotal;
        this.estadoReserva = estadoReserva;
        this.estadoPago = estadoPago;
        this.fechaCreacion = fechaCreacion;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public AmbienteEntity getAmbiente() {
        return ambiente;
    }
    
    public void setAmbiente(AmbienteEntity ambiente) {
        this.ambiente = ambiente;
    }
    
    public SocioEntity getSocio() {
        return socio;
    }
    
    public void setSocio(SocioEntity socio) {
        this.socio = socio;
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
}

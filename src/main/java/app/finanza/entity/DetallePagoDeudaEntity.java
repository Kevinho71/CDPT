package app.finanza.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pago_deuda")
public class DetallePagoDeudaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_transaccion", nullable = false)
    private TransaccionPagoEntity transaccion;
    
    @ManyToOne
    @JoinColumn(name = "fk_estado_cuenta", nullable = false)
    private EstadoCuentaSocioEntity estadoCuenta;
    
    @Column(name = "monto_aplicado", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoAplicado; // Cuánto de este pago se aplicó a esta deuda
    
    // Constructores
    public DetallePagoDeudaEntity() {}
    
    public DetallePagoDeudaEntity(Integer id, TransaccionPagoEntity transaccion,
                                 EstadoCuentaSocioEntity estadoCuenta, BigDecimal montoAplicado) {
        this.id = id;
        this.transaccion = transaccion;
        this.estadoCuenta = estadoCuenta;
        this.montoAplicado = montoAplicado;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public TransaccionPagoEntity getTransaccion() {
        return transaccion;
    }
    
    public void setTransaccion(TransaccionPagoEntity transaccion) {
        this.transaccion = transaccion;
    }
    
    public EstadoCuentaSocioEntity getEstadoCuenta() {
        return estadoCuenta;
    }
    
    public void setEstadoCuenta(EstadoCuentaSocioEntity estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }
    
    public BigDecimal getMontoAplicado() {
        return montoAplicado;
    }
    
    public void setMontoAplicado(BigDecimal montoAplicado) {
        this.montoAplicado = montoAplicado;
    }
}

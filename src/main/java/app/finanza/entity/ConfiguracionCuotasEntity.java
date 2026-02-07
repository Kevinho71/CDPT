package app.finanza.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "configuracion_cuotas")
public class ConfiguracionCuotasEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "gestion", unique = true, nullable = false)
    private Integer gestion; // Ej: 2025, 2026
    
    @Column(name = "monto_matricula", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoMatricula;
    
    @Column(name = "monto_mensualidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoMensualidad;
    
    @Column(name = "dia_limite_pago")
    private Integer diaLimitePago = 10; // Día del mes límite para pagar
    
    @Column(name = "estado")
    private Integer estado = 1;
    
    // Constructores
    public ConfiguracionCuotasEntity() {}
    
    public ConfiguracionCuotasEntity(Integer id, Integer gestion, BigDecimal montoMatricula,
                                    BigDecimal montoMensualidad, Integer diaLimitePago, Integer estado) {
        this.id = id;
        this.gestion = gestion;
        this.montoMatricula = montoMatricula;
        this.montoMensualidad = montoMensualidad;
        this.diaLimitePago = diaLimitePago;
        this.estado = estado;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getGestion() {
        return gestion;
    }
    
    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }
    
    public BigDecimal getMontoMatricula() {
        return montoMatricula;
    }
    
    public void setMontoMatricula(BigDecimal montoMatricula) {
        this.montoMatricula = montoMatricula;
    }
    
    public BigDecimal getMontoMensualidad() {
        return montoMensualidad;
    }
    
    public void setMontoMensualidad(BigDecimal montoMensualidad) {
        this.montoMensualidad = montoMensualidad;
    }
    
    public Integer getDiaLimitePago() {
        return diaLimitePago;
    }
    
    public void setDiaLimitePago(Integer diaLimitePago) {
        this.diaLimitePago = diaLimitePago;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}

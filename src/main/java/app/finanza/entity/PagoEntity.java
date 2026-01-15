package app.finanza.entity;

import app.socio.entity.SocioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class PagoEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_socio", nullable = false)
    private SocioEntity socio;
    
    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    
    @Column(name = "moneda", length = 10)
    private String moneda = "BOB";
    
    @Column(name = "concepto", length = 255)
    private String concepto;
    
    @Column(name = "metodo_pago", length = 50)
    private String metodoPago;
    
    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago = LocalDateTime.now();
    
    @Column(name = "estado", length = 20)
    private String estado = "aprobado";
    
    @Column(name = "factura_url", length = 500)
    private String facturaUrl;
    
    // Constructores
    public PagoEntity() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public SocioEntity getSocio() {
        return socio;
    }
    
    public void setSocio(SocioEntity socio) {
        this.socio = socio;
    }
    
    public BigDecimal getMonto() {
        return monto;
    }
    
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
    public String getMoneda() {
        return moneda;
    }
    
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    
    public String getConcepto() {
        return concepto;
    }
    
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    
    public LocalDateTime getFechaPago() {
        return fechaPago;
    }
    
    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getFacturaUrl() {
        return facturaUrl;
    }
    
    public void setFacturaUrl(String facturaUrl) {
        this.facturaUrl = facturaUrl;
    }
}

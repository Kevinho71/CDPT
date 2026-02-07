package app.finanza.entity;

import app.core.entity.UsuarioEntity;
import app.socio.entity.SocioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaccion_pago")
public class TransaccionPagoEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_socio", nullable = false)
    private SocioEntity socio;
    
    @ManyToOne
    @JoinColumn(name = "fk_usuario_admin")
    private UsuarioEntity usuarioAdmin; // Admin que registra el pago manualmente
    
    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;
    
    @Column(name = "metodo_pago", length = 50)
    private String metodoPago; // QR, TRANSFERENCIA, EFECTIVO
    
    @Column(name = "comprobante_url", length = 500)
    private String comprobanteUrl;
    
    @Column(name = "referencia_bancaria", length = 100)
    private String referenciaBancaria;
    
    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago = LocalDateTime.now();
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "estado", length = 20)
    private String estado = "APROBADO"; // EN_REVISION, APROBADO, RECHAZADO
    
    // Constructores
    public TransaccionPagoEntity() {}
    
    public TransaccionPagoEntity(Integer id, SocioEntity socio, UsuarioEntity usuarioAdmin,
                                BigDecimal montoTotal, String metodoPago, String comprobanteUrl,
                                String referenciaBancaria, LocalDateTime fechaPago,
                                String observaciones, String estado) {
        this.id = id;
        this.socio = socio;
        this.usuarioAdmin = usuarioAdmin;
        this.montoTotal = montoTotal;
        this.metodoPago = metodoPago;
        this.comprobanteUrl = comprobanteUrl;
        this.referenciaBancaria = referenciaBancaria;
        this.fechaPago = fechaPago;
        this.observaciones = observaciones;
        this.estado = estado;
    }
    
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
    
    public UsuarioEntity getUsuarioAdmin() {
        return usuarioAdmin;
    }
    
    public void setUsuarioAdmin(UsuarioEntity usuarioAdmin) {
        this.usuarioAdmin = usuarioAdmin;
    }
    
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }
    
    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    
    public String getComprobanteUrl() {
        return comprobanteUrl;
    }
    
    public void setComprobanteUrl(String comprobanteUrl) {
        this.comprobanteUrl = comprobanteUrl;
    }
    
    public String getReferenciaBancaria() {
        return referenciaBancaria;
    }
    
    public void setReferenciaBancaria(String referenciaBancaria) {
        this.referenciaBancaria = referenciaBancaria;
    }
    
    public LocalDateTime getFechaPago() {
        return fechaPago;
    }
    
    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
}

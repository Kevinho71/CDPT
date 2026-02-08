package app.finanza.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransaccionPagoResponseDTO {
    
    private Integer id;
    private Integer fkSocio;
    private String socioNombre; // Nombre del socio que realizó el pago
    private Integer fkUsuarioAdmin;
    private String usuarioAdminNombre; // Nombre del admin que registró
    private BigDecimal montoTotal;
    private String metodoPago;
    private String comprobanteUrl;
    private String referenciaBancaria;
    private LocalDateTime fechaPago;
    private String observaciones;
    private String estado;
    
    // Detalles de conciliación FIFO (solo se llena al aprobar un pago)
    private List<DetalleConciliacionDTO> detallesConciliacion;
    
    // Constructor vacío
    public TransaccionPagoResponseDTO() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getFkSocio() {
        return fkSocio;
    }
    
    public void setFkSocio(Integer fkSocio) {
        this.fkSocio = fkSocio;
    }
    
    public String getSocioNombre() {
        return socioNombre;
    }
    
    public void setSocioNombre(String socioNombre) {
        this.socioNombre = socioNombre;
    }
    
    public Integer getFkUsuarioAdmin() {
        return fkUsuarioAdmin;
    }
    
    public void setFkUsuarioAdmin(Integer fkUsuarioAdmin) {
        this.fkUsuarioAdmin = fkUsuarioAdmin;
    }
    
    public String getUsuarioAdminNombre() {
        return usuarioAdminNombre;
    }
    
    public void setUsuarioAdminNombre(String usuarioAdminNombre) {
        this.usuarioAdminNombre = usuarioAdminNombre;
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
    
    public List<DetalleConciliacionDTO> getDetallesConciliacion() {
        return detallesConciliacion;
    }
    
    public void setDetallesConciliacion(List<DetalleConciliacionDTO> detallesConciliacion) {
        this.detallesConciliacion = detallesConciliacion;
    }
}

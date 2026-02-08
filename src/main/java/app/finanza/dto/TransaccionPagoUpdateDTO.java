package app.finanza.dto;

import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransaccionPagoUpdateDTO {
    
    private BigDecimal montoTotal;
    
    @Size(max = 50, message = "El método de pago no puede exceder 50 caracteres")
    private String metodoPago;
    
    @Size(max = 100, message = "La referencia bancaria no puede exceder 100 caracteres")
    private String referenciaBancaria;
    
    private String comprobanteUrl;
    
    @Size(max = 20, message = "El estado no puede exceder 20 caracteres")
    private String estado; // EN_REVISION, APROBADO, RECHAZADO
    
    private String observaciones;
    
    private LocalDateTime fechaPago;
    
    // Constructor vacío
    public TransaccionPagoUpdateDTO() {}
    
    // Getters y Setters
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
    
    public String getReferenciaBancaria() {
        return referenciaBancaria;
    }
    
    public void setReferenciaBancaria(String referenciaBancaria) {
        this.referenciaBancaria = referenciaBancaria;
    }
    
    public String getComprobanteUrl() {
        return comprobanteUrl;
    }
    
    public void setComprobanteUrl(String comprobanteUrl) {
        this.comprobanteUrl = comprobanteUrl;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public LocalDateTime getFechaPago() {
        return fechaPago;
    }
    
    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }
}

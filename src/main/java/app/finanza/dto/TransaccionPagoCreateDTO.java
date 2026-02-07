package app.finanza.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransaccionPagoCreateDTO {
    
    @NotNull(message = "El ID del socio es obligatorio")
    private Integer fkSocio;
    
    private Integer fkUsuarioAdmin; // Opcional: si un admin registra el pago
    
    @NotNull(message = "El monto total es obligatorio")
    private BigDecimal montoTotal;
    
    @NotBlank(message = "El método de pago es obligatorio")
    @Size(max = 50, message = "El método de pago no puede exceder 50 caracteres")
    private String metodoPago; // QR, TRANSFERENCIA, EFECTIVO
    
    // Para upload de comprobante
    private MultipartFile comprobante;
    
    // O si ya fue subido
    private String comprobanteUrl;
    
    @Size(max = 100, message = "La referencia bancaria no puede exceder 100 caracteres")
    private String referenciaBancaria;
    
    private LocalDateTime fechaPago = LocalDateTime.now();
    
    private String observaciones;
    
    @Size(max = 20, message = "El estado no puede exceder 20 caracteres")
    private String estado = "APROBADO"; // EN_REVISION, APROBADO, RECHAZADO
    
    // Constructor vacío
    public TransaccionPagoCreateDTO() {}
    
    // Getters y Setters
    public Integer getFkSocio() {
        return fkSocio;
    }
    
    public void setFkSocio(Integer fkSocio) {
        this.fkSocio = fkSocio;
    }
    
    public Integer getFkUsuarioAdmin() {
        return fkUsuarioAdmin;
    }
    
    public void setFkUsuarioAdmin(Integer fkUsuarioAdmin) {
        this.fkUsuarioAdmin = fkUsuarioAdmin;
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
    
    public MultipartFile getComprobante() {
        return comprobante;
    }
    
    public void setComprobante(MultipartFile comprobante) {
        this.comprobante = comprobante;
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

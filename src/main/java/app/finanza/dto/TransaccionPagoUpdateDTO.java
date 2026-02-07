package app.finanza.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class TransaccionPagoUpdateDTO {
    
    @Size(max = 20, message = "El estado no puede exceder 20 caracteres")
    private String estado; // EN_REVISION, APROBADO, RECHAZADO
    
    private String observaciones;
    
    private LocalDateTime fechaPago;
    
    // Constructor vacío
    public TransaccionPagoUpdateDTO() {}
    
    // Getters y Setters
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

package app.finanza.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ConfiguracionCuotasCreateDTO {
    
    @NotNull(message = "La gestión es obligatoria")
    private Integer gestion;
    
    @NotNull(message = "El monto de matrícula es obligatorio")
    private BigDecimal montoMatricula;
    
    @NotNull(message = "El monto de mensualidad es obligatorio")
    private BigDecimal montoMensualidad;
    
    private Integer diaLimitePago = 10;
    
    // Constructor vacío
    public ConfiguracionCuotasCreateDTO() {}
    
    // Getters y Setters
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
}

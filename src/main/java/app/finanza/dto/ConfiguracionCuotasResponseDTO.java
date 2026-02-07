package app.finanza.dto;

import java.math.BigDecimal;

public class ConfiguracionCuotasResponseDTO {
    
    private Integer id;
    private Integer gestion;
    private BigDecimal montoMatricula;
    private BigDecimal montoMensualidad;
    private Integer diaLimitePago;
    private Integer estado;
    
    // Constructor vacío
    public ConfiguracionCuotasResponseDTO() {}
    
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

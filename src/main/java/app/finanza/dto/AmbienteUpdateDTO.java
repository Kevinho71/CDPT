package app.finanza.dto;

import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class AmbienteUpdateDTO {
    
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    private String descripcion;
    
    private BigDecimal precioHora;
    
    private Integer estado;
    
    // Constructor vacío
    public AmbienteUpdateDTO() {}
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public BigDecimal getPrecioHora() {
        return precioHora;
    }
    
    public void setPrecioHora(BigDecimal precioHora) {
        this.precioHora = precioHora;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}

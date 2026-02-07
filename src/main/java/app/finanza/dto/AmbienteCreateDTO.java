package app.finanza.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class AmbienteCreateDTO {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    private String descripcion;
    
    @NotNull(message = "El precio por hora es obligatorio")
    private BigDecimal precioHora;
    
    // Constructor vacío
    public AmbienteCreateDTO() {}
    
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
}

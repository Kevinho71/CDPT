package app.reservas.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO para crear/actualizar ambientes.
 */
public class AmbienteDTO {
    
    private Integer id;
    
    @NotNull(message = "El nombre del ambiente es obligatorio")
    private String nombre;
    
    private String descripcion;
    
    @NotNull(message = "El precio por hora es obligatorio")
    private BigDecimal precioHora;
    
    private Integer estado;
    
    // Constructores
    public AmbienteDTO() {}
    
    public AmbienteDTO(Integer id, String nombre, String descripcion, BigDecimal precioHora, Integer estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioHora = precioHora;
        this.estado = estado;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
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

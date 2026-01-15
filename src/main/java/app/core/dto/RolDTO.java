package app.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RolDTO {
    
    @NotBlank(message = "El nombre del rol es requerido")
    private String nombre;
    
    @NotNull(message = "El estado es requerido")
    private Integer estado;
    
    // Constructores
    public RolDTO() {}
    
    public RolDTO(String nombre, Integer estado) {
        this.nombre = nombre;
        this.estado = estado;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "RolDTO{" +
                "nombre='" + nombre + '\'' +
                ", estado=" + estado +
                '}';
    }
}

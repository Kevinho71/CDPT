package app.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProfesionDTO {
    
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    
    @NotNull(message = "El estado es requerido")
    private Integer estado;
    
    // Constructores
    public ProfesionDTO() {}
    
    public ProfesionDTO(String nombre, Integer estado) {
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
        return "ProfesionDTO{" +
                "nombre='" + nombre + '\'' +
                ", estado=" + estado +
                '}';
    }
}

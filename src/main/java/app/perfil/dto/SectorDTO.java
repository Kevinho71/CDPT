package app.perfil.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SectorDTO {
    
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    
    private String descripcion;
    private String icono;
    
    @NotNull(message = "El estado es requerido")
    private Integer estado;
    
    // Constructores
    public SectorDTO() {}
    
    public SectorDTO(String nombre, String descripcion, String icono, Integer estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
        this.estado = estado;
    }
    
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
    
    public String getIcono() {
        return icono;
    }
    
    public void setIcono(String icono) {
        this.icono = icono;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "SectorDTO{" +
                "nombre='" + nombre + '\'' +
                ", estado=" + estado +
                '}';
    }
}

package app.perfil.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ServicioDTO {
    
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    
    private String descripcion;
    private String categoria;
    
    @NotNull(message = "El estado es requerido")
    private Integer estado;
    
    // Constructores
    public ServicioDTO() {}
    
    public ServicioDTO(String nombre, String descripcion, String categoria, Integer estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
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
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "ServicioDTO{" +
                "nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", estado=" + estado +
                '}';
    }
}

package app.perfil.dto;

import java.time.LocalDateTime;

public class IdiomaResponseDTO {
    
    private Integer id;
    private String nombre;
    private Integer estado;
    private LocalDateTime fechaCreacion;
    
    // Constructores
    public IdiomaResponseDTO() {}
    
    public IdiomaResponseDTO(Integer id, String nombre, Integer estado, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
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
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    @Override
    public String toString() {
        return "IdiomaResponseDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

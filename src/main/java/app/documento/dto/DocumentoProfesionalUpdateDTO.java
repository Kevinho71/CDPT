package app.documento.dto;

import jakarta.validation.constraints.Size;

public class DocumentoProfesionalUpdateDTO {
    
    @Size(max = 255, message = "El título no puede exceder 255 caracteres")
    private String titulo;
    
    private String descripcion;
    
    private Integer estado;
    
    // Constructor vacío
    public DocumentoProfesionalUpdateDTO() {}
    
    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}

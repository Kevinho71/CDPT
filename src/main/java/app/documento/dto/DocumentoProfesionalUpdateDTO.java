package app.documento.dto;

import jakarta.validation.constraints.Size;

public class DocumentoProfesionalUpdateDTO {
    
    @Size(max = 255, message = "El título no puede exceder 255 caracteres")
    private String titulo;
    
    private String descripcion;
    
    private Integer estado;

    @Size(max = 50, message = "El tipo de archivo no puede exceder 50 caracteres")
    private String tipoArchivo; // PDF, JPG, PNG

    private String archivoUrl;
    
    public String getArchivoUrl() {
        return archivoUrl;
    }

    public void setArchivoUrl(String archivoUrl) {
        this.archivoUrl = archivoUrl;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

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

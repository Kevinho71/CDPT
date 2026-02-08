package app.documento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class DocumentoProfesionalCreateDTO {
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede exceder 255 caracteres")
    private String titulo;
    
    private String descripcion;
    
    // Para upload de archivo
    private MultipartFile archivo;
    
    // O si ya fue subido previamente
    private String archivoUrl;
    
    @Size(max = 50, message = "El tipo de archivo no puede exceder 50 caracteres")
    private String tipoArchivo; // PDF, JPG, PNG
    
    // Constructor vacío
    public DocumentoProfesionalCreateDTO() {}
    
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
    
    public MultipartFile getArchivo() {
        return archivo;
    }
    
    public void setArchivo(MultipartFile archivo) {
        this.archivo = archivo;
    }
    
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
}

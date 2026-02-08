package app.perfil.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO para subir un nuevo documento profesional desde el perfil del socio
 * Combina la creación del documento + la asociación con el perfil
 */
public class SocioDocumentoUploadDTO {
    
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    
    private String descripcion;
    
    @NotNull(message = "El archivo es obligatorio")
    private MultipartFile archivo;
    
    private Integer orden = 0;
    
    private Boolean esVisible = true;
    
    // Constructores
    public SocioDocumentoUploadDTO() {}
    
    public SocioDocumentoUploadDTO(String titulo, String descripcion, MultipartFile archivo, 
                                   Integer orden, Boolean esVisible) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.orden = orden;
        this.esVisible = esVisible;
    }
    
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
    
    public Integer getOrden() {
        return orden;
    }
    
    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    
    public Boolean getEsVisible() {
        return esVisible;
    }
    
    public void setEsVisible(Boolean esVisible) {
        this.esVisible = esVisible;
    }
}

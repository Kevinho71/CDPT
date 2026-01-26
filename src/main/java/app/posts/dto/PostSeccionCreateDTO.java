package app.posts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO para crear o actualizar una sección individual
 */
public class PostSeccionCreateDTO {
    
    @NotNull(message = "El orden de la sección es obligatorio")
    private Integer orden;
    
    @NotBlank(message = "El tipo de sección es obligatorio")
    @Size(max = 20, message = "El tipo de sección no puede exceder 20 caracteres")
    private String tipoSeccion; // 'ESTANDAR', 'VIDEO', 'CITA'
    
    @Size(max = 255, message = "El subtítulo no puede exceder 255 caracteres")
    private String subtitulo;
    
    private String contenido; // HTML String - puede ser largo
    
    @Size(max = 500, message = "La URL de imagen no puede exceder 500 caracteres")
    private String imagenUrl; // Cloudinary URL
    
    @Size(max = 500, message = "La URL de video no puede exceder 500 caracteres")
    private String videoUrl; // YouTube URL

    // Constructor vacío
    public PostSeccionCreateDTO() {}

    // Constructor completo
    public PostSeccionCreateDTO(Integer orden, String tipoSeccion, String subtitulo, 
                               String contenido, String imagenUrl, String videoUrl) {
        this.orden = orden;
        this.tipoSeccion = tipoSeccion;
        this.subtitulo = subtitulo;
        this.contenido = contenido;
        this.imagenUrl = imagenUrl;
        this.videoUrl = videoUrl;
    }

    // Getters y Setters
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getTipoSeccion() {
        return tipoSeccion;
    }

    public void setTipoSeccion(String tipoSeccion) {
        this.tipoSeccion = tipoSeccion;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}

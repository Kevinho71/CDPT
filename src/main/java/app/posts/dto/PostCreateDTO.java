package app.posts.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para crear un nuevo post (noticia o evento)
 */
public class PostCreateDTO {
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede exceder 255 caracteres")
    private String titulo;
    
    @NotBlank(message = "La introducción es obligatoria")
    private String intro;
    
    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 100, message = "El autor no puede exceder 100 caracteres")
    private String autor; // Texto público (Ej: "La Directiva")
    
    @Size(max = 500, message = "La URL de portada no puede exceder 500 caracteres")
    private String portadaUrl; // Cloudinary URL
    
    @NotBlank(message = "El tipo de post es obligatorio")
    @Size(max = 20, message = "El tipo no puede exceder 20 caracteres")
    private String tipo; // 'NOTICIA' o 'EVENTO'
    
    private Boolean publicado = false; // Por defecto es borrador
    
    // Campos específicos para EVENTOS (nullables si es NOTICIA)
    private LocalDateTime fechaEvento;
    
    @Size(max = 255, message = "El lugar del evento no puede exceder 255 caracteres")
    private String lugarEvento;
    
    @Size(max = 500, message = "La dirección del evento no puede exceder 500 caracteres")
    private String direccionEvento;
    
    // Lista de secciones de contenido
    @Valid
    private List<PostSeccionCreateDTO> secciones = new ArrayList<>();

    // Constructor vacío
    public PostCreateDTO() {}

    // Constructor completo
    public PostCreateDTO(String titulo, String intro, String autor, String portadaUrl, 
                        String tipo, Boolean publicado, LocalDateTime fechaEvento, 
                        String lugarEvento, String direccionEvento, List<PostSeccionCreateDTO> secciones) {
        this.titulo = titulo;
        this.intro = intro;
        this.autor = autor;
        this.portadaUrl = portadaUrl;
        this.tipo = tipo;
        this.publicado = publicado;
        this.fechaEvento = fechaEvento;
        this.lugarEvento = lugarEvento;
        this.direccionEvento = direccionEvento;
        this.secciones = secciones != null ? secciones : new ArrayList<>();
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getPortadaUrl() {
        return portadaUrl;
    }

    public void setPortadaUrl(String portadaUrl) {
        this.portadaUrl = portadaUrl;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getPublicado() {
        return publicado;
    }

    public void setPublicado(Boolean publicado) {
        this.publicado = publicado;
    }

    public LocalDateTime getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }

    public String getDireccionEvento() {
        return direccionEvento;
    }

    public void setDireccionEvento(String direccionEvento) {
        this.direccionEvento = direccionEvento;
    }

    public List<PostSeccionCreateDTO> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<PostSeccionCreateDTO> secciones) {
        this.secciones = secciones;
    }
}

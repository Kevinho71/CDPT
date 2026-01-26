package app.posts.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO completo para vista de detalle del post
 * Incluye todas las secciones ordenadas
 */
public class PostDetailDTO {
    private Integer id;
    private String titulo;
    private String slug;
    private String intro;
    private String portadaUrl;
    private String autor;
    private String tipo; // 'NOTICIA' o 'EVENTO'
    private LocalDateTime createdAt;
    private Boolean publicado;
    
    // Campos específicos de eventos
    private LocalDateTime fechaEvento;
    private String lugarEvento;
    private String direccionEvento;
    
    // Lista completa de secciones ordenadas
    private List<PostSeccionDTO> secciones = new ArrayList<>();
    
    // Información del usuario autor (opcional)
    private String usuarioNombre;

    // Constructor vacío
    public PostDetailDTO() {}

    // Constructor completo
    public PostDetailDTO(Integer id, String titulo, String slug, String intro, 
                        String portadaUrl, String autor, String tipo, LocalDateTime createdAt, 
                        Boolean publicado, LocalDateTime fechaEvento, String lugarEvento, 
                        String direccionEvento, List<PostSeccionDTO> secciones, String usuarioNombre) {
        this.id = id;
        this.titulo = titulo;
        this.slug = slug;
        this.intro = intro;
        this.portadaUrl = portadaUrl;
        this.autor = autor;
        this.tipo = tipo;
        this.createdAt = createdAt;
        this.publicado = publicado;
        this.fechaEvento = fechaEvento;
        this.lugarEvento = lugarEvento;
        this.direccionEvento = direccionEvento;
        this.secciones = secciones != null ? secciones : new ArrayList<>();
        this.usuarioNombre = usuarioNombre;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPortadaUrl() {
        return portadaUrl;
    }

    public void setPortadaUrl(String portadaUrl) {
        this.portadaUrl = portadaUrl;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public List<PostSeccionDTO> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<PostSeccionDTO> secciones) {
        this.secciones = secciones;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }
}

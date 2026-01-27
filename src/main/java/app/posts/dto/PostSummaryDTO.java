package app.posts.dto;

import java.time.LocalDateTime;

/**
 * DTO ligero para listados de posts en la landing page
 * NO contiene la lista de secciones para optimizar tráfico
 */
public class PostSummaryDTO {
    private Integer id;
    private String titulo;
    private String slug;
    private String intro;
    private String portadaUrl;
    private String autor;
    private String tipo; // 'NOTICIA' o 'EVENTO'
    private LocalDateTime createdAt;
    private LocalDateTime fechaEvento; // Solo para eventos
    private Boolean publicado;

    // Constructor vacío
    public PostSummaryDTO() {}

    // Constructor completo para JPQL projections
    public PostSummaryDTO(Integer id, String titulo, String slug, String intro, 
                         String portadaUrl, String autor, String tipo, 
                         LocalDateTime createdAt, LocalDateTime fechaEvento, Boolean publicado) {
        this.id = id;
        this.titulo = titulo;
        this.slug = slug;
        this.intro = intro;
        this.portadaUrl = portadaUrl;
        this.autor = autor;
        this.tipo = tipo;
        this.createdAt = createdAt;
        this.fechaEvento = fechaEvento;
        this.publicado = publicado;
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

    public LocalDateTime getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Boolean getPublicado() {
        return publicado;
    }

    public void setPublicado(Boolean publicado) {
        this.publicado = publicado;
    }
}

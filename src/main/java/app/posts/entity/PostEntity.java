package app.posts.entity;

import app.core.entity.UsuarioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class PostEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private Integer estado;

    // RELACIÓN CON USUARIO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario")
    private UsuarioEntity usuario;

    // DATOS OBLIGATORIOS COMUNES
    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(unique = true, nullable = false, length = 255)
    private String slug;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String intro;

    @Column(name = "portada_url", length = 500)
    private String portadaUrl;

    @Column(nullable = false, length = 100)
    private String autor;

    // CLASIFICACIÓN Y ESTADO
    @Column(nullable = false, length = 20)
    private String tipo; // 'NOTICIA' o 'EVENTO'

    @Column(nullable = false)
    private Boolean publicado = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // DATOS EXCLUSIVOS DE EVENTOS
    @Column(name = "fecha_evento")
    private LocalDateTime fechaEvento;

    @Column(name = "lugar_evento", length = 255)
    private String lugarEvento;

    @Column(name = "direccion_evento", length = 500)
    private String direccionEvento;

    // RELACIÓN CON SECCIONES
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orden ASC")
    private List<PostSeccionEntity> secciones = new ArrayList<>();

    // CONSTRUCTORES
    public PostEntity() {
        this.createdAt = LocalDateTime.now();
    }

    public PostEntity(Integer id, Integer estado, UsuarioEntity usuario, String titulo, 
                      String slug, String intro, String portadaUrl, String autor, String tipo, 
                      Boolean publicado, LocalDateTime fechaEvento, String lugarEvento, String direccionEvento) {
        this.id = id;
        this.estado = estado;
        this.usuario = usuario;
        this.titulo = titulo;
        this.slug = slug;
        this.intro = intro;
        this.portadaUrl = portadaUrl;
        this.autor = autor;
        this.tipo = tipo;
        this.publicado = publicado;
        this.createdAt = LocalDateTime.now();
        this.fechaEvento = fechaEvento;
        this.lugarEvento = lugarEvento;
        this.direccionEvento = direccionEvento;
    }

    // MÉTODOS DE AYUDA PARA GESTIONAR SECCIONES
    public void addSeccion(PostSeccionEntity seccion) {
        secciones.add(seccion);
        seccion.setPost(this);
    }

    public void removeSeccion(PostSeccionEntity seccion) {
        secciones.remove(seccion);
        seccion.setPost(null);
    }

    // GETTERS Y SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
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

    public Boolean getPublicado() {
        return publicado;
    }

    public void setPublicado(Boolean publicado) {
        this.publicado = publicado;
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

    public List<PostSeccionEntity> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<PostSeccionEntity> secciones) {
        this.secciones = secciones;
    }

    @Override
    public String toString() {
        return "PostEntity{" +
                "id=" + id +
                ", estado=" + estado +
                ", titulo='" + titulo + '\'' +
                ", slug='" + slug + '\'' +
                ", tipo='" + tipo + '\'' +
                ", publicado=" + publicado +
                ", createdAt=" + createdAt +
                '}';
    }
}

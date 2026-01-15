package app.publicacion.entity;

import app.core.entity.UsuarioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publicaciones")
public class PublicacionEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;
    
    @Column(name = "slug", nullable = false, unique = true, length = 300)
    private String slug; // URL amigable
    
    @Column(name = "resumen", columnDefinition = "TEXT")
    private String resumen;
    
    @Column(name = "contenido", columnDefinition = "TEXT")
    private String contenido;
    
    @Column(name = "tipo", length = 50)
    private String tipo = "noticia"; // 'noticia', 'evento'
    
    @Column(name = "fecha_evento")
    private LocalDateTime fechaEvento;
    
    @Column(name = "publicado")
    private Boolean publicado = true;
    
    @ManyToOne
    @JoinColumn(name = "fk_autor")
    private UsuarioEntity autor;
    
    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion = LocalDateTime.now();
    
    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PublicacionImagenEntity> imagenes = new ArrayList<>();
    
    // Constructores
    public PublicacionEntity() {}
    
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
    
    public String getResumen() {
        return resumen;
    }
    
    public void setResumen(String resumen) {
        this.resumen = resumen;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
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
    
    public UsuarioEntity getAutor() {
        return autor;
    }
    
    public void setAutor(UsuarioEntity autor) {
        this.autor = autor;
    }
    
    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }
    
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    
    public List<PublicacionImagenEntity> getImagenes() {
        return imagenes;
    }
    
    public void setImagenes(List<PublicacionImagenEntity> imagenes) {
        this.imagenes = imagenes;
    }
}
